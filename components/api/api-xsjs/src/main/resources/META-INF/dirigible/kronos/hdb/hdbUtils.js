/*
 * Copyright (c) 2022 codbex or an codbex affiliate company and contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2022 codbex or an codbex affiliate company and contributors
 * SPDX-License-Identifier: EPL-2.0
 */
export function getResultSetValueByDataTypeAndRowNumber (resultSet, dataType, colNumber) {
    switch (dataType) {
        case "TINYINT":
            return resultSet.getByte(colNumber);
        case "SMALLINT":
            return resultSet.getShort(colNumber);
        case "INTEGER":
            return resultSet.getInt(colNumber);
        case "BIGINT":
            return resultSet.getLong(colNumber);
        case "SMALLDECIMAL":
        case "DECIMAL":
            return resultSet.getBigDecimal(colNumber).toPlainString(); // convert to String as in HANA XSJS it is returned as String
        case "REAL":
        case "FLOAT":
            return resultSet.getFloat(colNumber);
        case "DOUBLE PRECISION":
        case "DOUBLE":
            return resultSet.getDouble(colNumber);
        case "CHARACTER VARYING":
        case "VARCHAR":
        case "ALPHANUM":
            return resultSet.getString(colNumber);
        case "NVARCHAR":
        case "SHORTTEXT":
            return resultSet.native.getNString(colNumber);
        case "VARBINARY":
            return resultSet.getBytes(colNumber);
        case "BOOLEAN":
            return resultSet.getBoolean(colNumber);
        case "DATE":
            return resultSet.getDate(colNumber);
        case "TIME":
            return resultSet.getTime(colNumber);
        case "TIMESTAMP":
            return resultSet.getTimestamp(colNumber);
        case "SECONDDATE":
            throw new Error(`The '${dataType}' data type in the resultSet is not supported`);
        case "BLOB":
            return resultSet.getBlob(colNumber);
        case "TEXT":
        case "CLOB":
            return resultSet.getClob(colNumber);
        case "ARRAY":
            throw new Error(`The '${dataType}' data type in the resultSet is not supported`);
        case "NCLOB":
            return resultSet.getNClob(colNumber);
        case "ST_GEOMETRY":
            throw new Error(`The '${dataType}' data type in the resultSet is not supported`);
        case "ST_POINT":
            throw new Error(`The '${dataType}' data type in the resultSet is not supported`);
        default:
            throw new Error(`The '${dataType}' data type in the resultSet is not supported`);
    }
};

export function setParamByType(preparedStatement, paramType, paramValue, paramIndex) {
    switch (paramType) {
        case "TINYINT":
            preparedStatement.setByte(paramIndex, paramValue);
            break;
        case "SMALLINT":
            preparedStatement.setShort(paramIndex, paramValue);
            break;
        case "INTEGER":
            preparedStatement.setInt(paramIndex, paramValue);
            break;
        case "BIGINT":
            preparedStatement.setLong(paramIndex, paramValue);
            break;
        case "SMALLDECIMAL":
        case "DECIMAL":
            preparedStatement.setBigDecimal(paramIndex, tryConvertNumberToBigDecimal(paramValue));
            break;
        case "REAL":
        case "FLOAT":
            preparedStatement.setFloat(paramIndex, paramValue);
            break;
        case "DOUBLE PRECISION":
        case "DOUBLE":
            preparedStatement.setDouble(paramIndex, paramValue);
            break;
        case "CHARACTER VARYING":
        case "VARCHAR":
        case "ALPHANUM":
            preparedStatement.setString(paramIndex, paramValue);
            break;
        case "NVARCHAR":
        case "NCHAR":
        case "SHORTTEXT":
            preparedStatement.native.setNString(paramIndex, paramValue);
            break;
        case "VARBINARY":
            preparedStatement.setBytes(paramIndex, paramValue);
            break;
        case "BOOLEAN":
            preparedStatement.setBoolean(paramIndex, paramValue);
            break;
        case "DATE":
            preparedStatement.setDate(paramIndex, paramValue);
            break;
        case "TIME":
            preparedStatement.setTime(paramIndex, paramValue);
            break;
        case "TIMESTAMP":
            preparedStatement.setTimestamp(paramIndex, paramValue);
            break;
        case "SECONDDATE":
            throw new Error(`The '${paramType}' data type in the preparedStatement is not supported`);
        case "BLOB":
            preparedStatement.setBlob(paramIndex, paramValue);
            break;
        case "TEXT":
        case "CLOB":
            preparedStatement.setClob(paramIndex, paramValue);
            break;
        case "ARRAY":
            throw new Error(`The '${paramType}' data type in the preparedStatement is not supported`);
        case "NCLOB":
            preparedStatement.setNClob(paramIndex, paramValue);
            break;
        case "ST_GEOMETRY":
            throw new Error(`The '${paramType}' data type in the preparedStatement is not supported`);
        case "ST_POINT":
            throw new Error(`The '${paramType}' data type in the preparedStatement is not supported`);
        default:
            throw new Error(`The '${paramType}' data type in the preparedStatement is not supported`);
    }
};

function tryConvertNumberToBigDecimal(maybeNumber) {
    if (typeof maybeNumber === 'number') {
        const BigDecimal = Java.type("java.math.BigDecimal");
        return BigDecimal.valueOf(maybeNumber);
    }
    return maybeNumber;
}