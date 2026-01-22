import { DatabaseInitializer } from "./database.mjs";
import { initializeABAP } from '../dist/abap/init.mjs';

import { zcl_abap_app } from '../dist/abap/zcl_abap_app.clas.mjs';

async function initialize() {
    DatabaseInitializer.initDefaultDataSource();
    await initializeABAP();
}

export async function execute(parameters = {}) {
    await initialize();
    return await zcl_abap_app.run(parameters);
}

const result = await execute();
console.log("Result: " + JSON.stringify(result));
