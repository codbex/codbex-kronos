import { assertEquals } from '../utils/utils.mjs'
import { XSJSLibCompiler } from './XSJSLibCompiler.mjs'
import { repository } from 'sdk/platform'

function testContentModifier_AcornSimpleContentModification() {
  const compiler = new XSJSLibCompiler();
  const input = "function asd(){ return 'asd'; }\nvar bcd = 3;\n function gfh(g){ return g; }";
  const expected = input + "\n\n" + "exports.asd = asd;\n" + "exports.gfh = gfh;\n" + "exports.bcd = bcd;\n";

  const actual = compiler.appendExports(input);

  assertEquals(expected, actual,
  "Unexpected content from XSJSLibCompiler::appendExports(). ");
}

testContentModifier_AcornSimpleContentModification();