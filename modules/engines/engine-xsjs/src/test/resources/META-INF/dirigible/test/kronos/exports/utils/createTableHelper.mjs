import { XSJSLibStateTable } from './XSJSLibStateTable.mjs'
import { repository } from '@dirigible/platform'

let stateTableParams = {
  name: "PROCESSED_XSJSLIB_ARTEFACTS",
  schema: "PUBLIC"
}

let table = new XSJSLibStateTable(
  stateTableParams.name,
  stateTableParams.schema
);

repository.createResource("testFolder/abc.xsjslib", "abc", "text/html");
table.createEntryForResource("testFolder/abc.xsjslib", "abc");
