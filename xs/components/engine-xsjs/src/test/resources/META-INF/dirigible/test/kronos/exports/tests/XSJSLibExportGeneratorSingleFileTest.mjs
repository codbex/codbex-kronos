import { assertEquals } from '../utils/utils.mjs'
import { getParams } from '../utils/stateTableParamsProvider.mjs'
import { fetchAllEntriesInTable } from '../utils/utils.mjs'
import { XSJSLibExportsGenerator } from './XSJSLibExportsGenerator.mjs'
import { repository } from 'sdk/platform'
import { digest } from 'sdk/utils'
const XSJSLibSynchronizerRegistryEntity = Java.type("com.codbex.kronos.synchronizer.XSJSLibSynchronizerRegistryEntity");

function testSingleFileExportGeneration() {
  const stateTableParams = getParams();
  const input = "function asd(){ return 'asd'; }\nvar bcd = 3;\n function gfh(g){ return g; }";
  const expectedContent = input + "\n\n" + "exports.asd = asd;\n" + "exports.gfh = gfh;\n" + "exports.bcd = bcd;\n";

  // create a new resource with some xsjslib content
  const collection = repository.createCollection("asd");
  const resource = repository.createResource("asd/asd.xsjslib", input, "text/html");

  // run generation and assert content is valid
  const generator = new XSJSLibExportsGenerator(stateTableParams);
  const target = new XSJSLibSynchronizerRegistryEntity(collection.getPath(), repository);
  generator.run(target);
  const generatedExportsResource = repository.getResource(resource.getPath() + "_generated_exports.js");
  assertEquals(expectedContent, generatedExportsResource.getText(), "Unexpected xsjslib content after exports generation.");
  assertEquals(input, resource.getText(), "Unexpected xsjslib content after exports generation.");

  // assert state table entries are okay
  const entries = fetchAllEntriesInTable(stateTableParams);
  const expected = {"ID":0, "LOCATION":"/asd/asd.xsjslib", "HASH": digest.md5Hex(input)};

  assertEquals(1, entries.length, "Unexpected count of entries in DB.");
  const actual = entries.shift();

  assertEquals(JSON.stringify(Object.keys(expected)), JSON.stringify(Object.keys(actual)), "Unexpected entry keys.");
  assertEquals(expected.LOCATION, actual.LOCATION, "Unexpected entry location.");
  assertEquals(expected.HASH, actual.HASH, "Unexpected entry hash.");
}

testSingleFileExportGeneration();