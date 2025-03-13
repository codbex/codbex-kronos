import { Assert } from 'test/assert';

var util = $.util;
var response = require('http/response');
var random1 = util.createUuid();
var random2 = util.createUuid();

var arrayBuffer = [84, 104, 105, 115, 32, 105, 115, 32, 97, 32, 85, 105, 110, 116,
  56, 65, 114, 114, 97, 121, 32, 99, 111, 110, 118, 101, 114, 116,
  101, 100, 32, 116, 111, 32, 97, 32, 115, 116, 114, 105, 110, 103];
var convertedBuff = util.stringify(arrayBuffer);

var zipSource = [123, 34, 107, 114, 111, 110, 111, 115, 46, 116, 120, 116, 34, 58, 34, 84, 104, 105, 115, 32, 105, 115, 32, 75, 114, 111, 110, 111, 115, 34, 125];

var zip1 = new util.Zip();
zip1['kronos.txt'] = 'This is Kronos';
var zip1ArrayBuffer = zip1.asArrayBuffer();

var zip2 = new util.Zip({source: zipSource});

Assert.assertTrue(random1 != random2 &&
convertedBuff === "This is a Uint8Array converted to a string" &&
JSON.stringify(zip1ArrayBuffer) === JSON.stringify(zipSource) &&
zip2['kronos.txt'] === 'This is Kronos'
)
