import { DB } from "@abaplint/runtime";
import { database, sql, update, query } from "sdk/db";

import { logging } from "sdk/log";

const DatabaseResultSetHelper = Java.type("org.eclipse.dirigible.components.data.management.helpers.DatabaseResultSetHelper");

export class DatabaseInitializer {
    private static readonly DEFAULT_DATA_SOURCE_NAME = "DefaultDB";

    private static readonly logger = logging.getLogger("com.codbex.kronos.abap.database");

    public static initDefaultDataSource(datasourceName?: string | undefined) {
        DatabaseInitializer.logger.info("Init default data source for ABAP...");

        const defaultAbapDataSource = datasourceName ? datasourceName : DatabaseInitializer.DEFAULT_DATA_SOURCE_NAME;
        DatabaseInitializer.logger.info("Default ABAP data source will be [{}]", defaultAbapDataSource);

        abap.context.databaseConnections["DEFAULT"] = new DatabaseClient(defaultAbapDataSource);
        DatabaseInitializer.logger.info("Default data source for ABAP was initialized. [{}] data source will be used", defaultAbapDataSource);
    };
}

class DatabaseClient implements DB.DatabaseClient {

    public readonly name = "kronos-default-database";

    private readonly logger;
    private readonly datasourceName;
    private connection: any;

    public constructor(datasourceName: string) {
        this.logger = logging.getLogger("com.codbex.kronos.DatabaseClient");
        this.datasourceName = datasourceName;
    }

    public async connect() {
        this.logger.debug("Creating connection...");
        this.connection = database.getConnection(this.datasourceName);
        this.connection.setAutoCommit(false);
        // @ts-ignore
        if (abap?.context?.databaseConnections && abap.context.databaseConnections["DEFAULT"] === this) {
            // @ts-ignore
            abap.builtin.sy.get().dbsys?.set(this.name);
        }
        this.logger.debug("Created connection");
    }

    public async disconnect() {
        if (this.connection) {
            this.logger.debug("Closing connection...");
            this.connection.close();
            this.logger.debug("Connection was closed");
        }
    }

    public async execute(sql: string | string[]): Promise<void> {

        if (typeof sql !== "string") {
            for (const s of sql) {
                await this.execute(s);
            }
            return;
        }

        if (sql === "") {
            return;
        }
        if (!this.connection) {
            const errorMessage = `SQL [${sql}] cannot be executed because the connection is not inialized`;
            this.logger.error(errorMessage);
            throw new Error(errorMessage);
        }

        this.logger.debug("Executing sql [{}]", sql);

        try {
            const statement = this.connection.createStatement();
            const hasResultSet = statement.execute(sql);
            if (hasResultSet) {
                this.logger.debug("Executed sql [{}] has result set.", sql);
            }
        } catch (error) {
            const errorMessage = `Failed to execute [${sql}]. Error: [${error}]`;
            this.logger.error(errorMessage, error);
            throw new Error(errorMessage);
        }
    }

    public async beginTransaction() {
        this.connect();
    }

    public async commit() {
        if (this.connection) {
            this.logger.debug("Committing current connection...");
            this.connection.commit();
            this.logger.debug("Current connection was committed");
        } else {
            this.logger.warn("Connection not initialized and cannot be committed");
        }
        return;
    }

    public async rollback() {
        if (this.connection) {
            this.logger.debug("Rolling back current connection...");
            this.connection.rollback();
            this.logger.debug("Current connection was rollbacked");
        } else {
            this.logger.warn("Connection not initialized and cannot be rollbacked");
        }
    }

    public async delete(options: DB.DeleteDatabaseOptions) {
        this.logger.debug("Deleting using options [{}]", JSON.stringify(options));
        let sqlDelete = this.getDialect()//
            .delete()//
            .from(options.table)//
            .build();

        sqlDelete = sqlDelete + ` WHERE ${options.where}`;
        return this.executeUpdate(sqlDelete);
    }

    public async update(options: DB.UpdateDatabaseOptions) {
        this.logger.debug("Updating using options [{}]", JSON.stringify(options));
        let sqlUpdate = this.getDialect()//
            .update()//
            .table(options.table)//
            .build();

        // sqlUpdate already contains SET
        sqlUpdate = sqlUpdate + ` ${options.set.join(", ")} WHERE ${options.where}`;
        return this.executeUpdate(sqlUpdate);
    }

    public async insert(options: DB.InsertDatabaseOptions) {
        this.logger.debug("Inserting using options [{}]", JSON.stringify(options));
        const insertBuilder = this.getDialect()//
            .insert()//
            .into(options.table);

        options.columns.forEach((columnName: string, index: number) => {
            const columnValue = options.values[index];
            insertBuilder.column(columnName).value(columnValue);
        });

        const sqlInsert = insertBuilder.build();
        return this.executeUpdate(sqlInsert);
    }

    private getDialect() {
        const conn = database.getConnection(this.datasourceName);
        try {
            return sql.getDialect(conn);
        } finally {
            conn.close();
        }
    }

    private executeUpdate(sql: string) {
        this.logger.debug("Executing [{}]...", sql);

        try {
            const affectedRows = update.execute(sql, undefined, this.datasourceName);
            this.logger.debug("Affected [{}] rows by executing [{}]", affectedRows, sql);

            return this.createCRUDResult(affectedRows, 0)
        } catch (error) {
            this.logger.error(`Failed to execute [{}]. Error: [{}]`, sql, error);
            return this.createErrorCRUDResult();
        }
    }

    private createErrorCRUDResult() {
        return this.createCRUDResult(0, 4);
    }

    private createCRUDResult(affectedRows: number, error: number) {
        return {
            dbcnt: affectedRows,
            subrc: error
        }
    }

    public async select(options: DB.SelectDatabaseOptions) {
        let selectSQL = this.convertInputSelect(options.select, options.primaryKey);
        this.logger.debug("Executing select [{}]... Input options [{}]", selectSQL, JSON.stringify(options));

        try {
            const resultSet = query.execute(selectSQL, undefined, this.datasourceName);
            const selectDatabaseResult = { rows: resultSet };
            this.logger.debug("Result of select [{}]: [{}]", selectSQL, JSON.stringify(selectDatabaseResult));

            return selectDatabaseResult;
        } catch (error) {
            this.logger.error(`Failed to execute [${selectSQL}]. Error: [${error}]`, error);
            // @ts-ignore
            if (abap.Classes["CX_SY_DYNAMIC_OSQL_SEMANTICS"] !== undefined) {
                // @ts-ignore
                throw await new abap.Classes["CX_SY_DYNAMIC_OSQL_SEMANTICS"]().constructor_({ sqlmsg: error.message || "" });
            }
            throw error;
        }
    }

    private convertInputSelect(select: string, primaryKeys: string[] | undefined) {
        let convertedSelect = select.replace(/ UP TO (\d+) ROWS(.*)/i, "$2 LIMIT $1");
        if (primaryKeys) {
            convertedSelect = convertedSelect.replace(/ ORDER BY PRIMARY KEY/i, " ORDER BY " + primaryKeys.join(", "));
        } else {
            convertedSelect = convertedSelect.replace(/ ORDER BY PRIMARY KEY/i, "");
        }
        convertedSelect = convertedSelect.replace(/ ASCENDING/ig, " ASC");
        convertedSelect = convertedSelect.replace(/ DESCENDING/ig, " DESC");
        convertedSelect = convertedSelect.replace(/~/g, ".");

        return convertedSelect;
    }

    public async openCursor(options: DB.SelectDatabaseOptions): Promise<DB.DatabaseCursorCallbacks> {
        if (!this.connection) {
            const errorMessage = "Connection is not initialized. Consider calling connect method first.";
            this.logger.error(errorMessage);
            throw new Error(errorMessage);
        }

        const statement = this.connection.createStatement();

        const selectSQL = options.select;
        this.logger.debug("Executing [{}]...", selectSQL);

        const resultSet = statement.executeQuery(selectSQL);

        return {
            fetchNextCursor: (packageSize: number) => this.fetchNextCursor.bind(this)(packageSize, resultSet),
            closeCursor: () => this.closeCursor.bind(this)(resultSet),
        };
    }

    private async fetchNextCursor(packageSize: number, resultSet: any): Promise<DB.SelectDatabaseResult> {
        this.logger.debug("Fetching next cursor...");
        const stringify = false;
        const resultSetJson = DatabaseResultSetHelper.toJson(resultSet, packageSize, stringify);

        const selectDatabaseResult = {
            rows: JSON.parse(resultSetJson)
        };
        this.logger.debug("Retrieved data [{}]", JSON.stringify(selectDatabaseResult));

        return selectDatabaseResult;
    }

    private async closeCursor(resultSet: any): Promise<void> {
        if (resultSet && !resultSet.isClosed()) {
            this.logger.debug("Closing result set...")
            resultSet.close();
            this.logger.debug("Result set was closed")
        } else {
            this.logger.warn("Result set is not defined or it is already closed.")
        }
    }
}

// @ts-ignore
if (typeof module !== 'undefined') {
    // @ts-ignore
    module.exports = DatabaseInitializer;
}
