import { DatabaseInitializer } from "sdk/abap/database";
import { initializeABAP } from '../dist/abap/init.mjs';

import { zcl_abap_app } from '../dist/abap/zcl_abap_app.clas.mjs';

async function initialize() {
    DatabaseInitializer.initDefaultDataSource();
    await initializeABAP();
}

await initialize();

await zcl_abap_app.run();
