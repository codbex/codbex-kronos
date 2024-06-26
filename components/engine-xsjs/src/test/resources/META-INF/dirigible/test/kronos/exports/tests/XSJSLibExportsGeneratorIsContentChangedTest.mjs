import { assertEquals } from '../utils/utils.mjs'
import { getParams } from '../utils/stateTableParamsProvider.mjs'
import { XSJSLibExportsGenerator } from './XSJSLibExportsGenerator.mjs'
import { digest } from 'sdk/utils'
import { repository } from 'sdk/platform'

function isContentChangedTest() {
  const stateTableParams = getParams();
  const generator = new XSJSLibExportsGenerator(stateTableParams);

  const content = "function asd(){}"
  const changedContent = "function asd22{}";
  const foundEntry = {"ID":0, "LOCATION":"asd/asd.xsjslib", "HASH": digest.md5Hex(content)};

  assertEquals(false, generator._isContentChanged(foundEntry, content), "Unexpected content change check result.");
  assertEquals(true, generator._isContentChanged(foundEntry, changedContent), "Unexpected content change check result.");
}

isContentChangedTest();