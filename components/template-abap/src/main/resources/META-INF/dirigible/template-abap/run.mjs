import { initializeABAP } from './output/init.mjs';
import { zcl_hello_world } from './output/zcl_hello_world.clas.mjs';

await initializeABAP();

await zcl_hello_world.run();