/*
 * Copyright (c) 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-FileCopyrightText: 2023 SAP SE or an SAP affiliate company and Eclipse Dirigible contributors
 * SPDX-License-Identifier: EPL-2.0
 */
/**
 * Transforms a string to an alphanumeric sequence, stripping non-conformant characters for it.
 * @param string {String} the string to transform
 */

let LOWERCASEASCII = "abcdefghijklmnopqrstuvwxyz";
const UPPERCASEASCII = LOWERCASEASCII.toUpperCase();
const NUMBERS = "1234567890";

exports.toAlphanumeric = function(string){
	return string.replace(/[^A-Za-z0-9_]/g, '');
};
/**
 * Generates a random alphanumeric sequence with the specified length
 * @param length {Integer} Defaults to 4
 */
exports.randomString = function(length, charset){
  let text = "";
  if(length<1)
	length = 4;
  for (var i = 0; i < length; i++)
    text += charset.charAt(Math.floor(Math.random() * charset.length));
  return text;
};
/**
 * Generates a random alphanumeric sequence with the specified length
 * @param length {Integer} Defaults to 4
 * @param lowercase {Boolean} Defaults to true
 */
exports.alphanumeric = function(length, lowercase){
  let charset = LOWERCASEASCII+NUMBERS;
  if (!lowercase){
	charset+=UPPERCASEASCII;
  }
  return randomstring(length, charset);
};
/**
 * Generates a random ASCII sequence with the specified length
 * @param length {Integer} Defaults to 4
 * @param lowercase {Boolean} Defaults to true
 */
exports.alpha = function(length, lowercase){
  let charset = LOWERCASEASCII;
  if (!lowercase){
	charset+=UPPERCASEASCII;
  }
  return randomstring(length, charset);
};
/**
 * Generates a random numeric value
 * @param length {Integer} Defaults to 4
 */
exports.numeric = function(length){
  return randomstring(length, NUMBERS);
};
/**
 * Tests is the provided `str` argument is a valid numeric sequence.
 * @param str {String} the string to test
 */
exports.isNumeric = function(str){
	// a faster alternative to checking with  /[^a-zA-Z0-9]/.test(str)
	// copy from public domain at: https://stackoverflow.com/a/25352300
	let code, i, len;
	for (i = 0, len = str.length; i < len; i++) {
		code = str.charCodeAt(i);
		if (!(code > 47 && code < 58)) { // numeric (0-9)
		  return false;
		}
	  }
	return true;
}
/**
 * Tests is the provided `str` argument is a valid alphanumeric sequence.
 * @param str {String} the string to test
 */
exports.isAlphanumeric = function(str){
	// a faster alternative to checking with  /[^a-zA-Z0-9]/.test(str)
	// copy from public domain at: https://stackoverflow.com/a/25352300
	let code, i, len;
	for (i = 0, len = str.length; i < len; i++) {
		code = str.charCodeAt(i);
		if (!(code > 47 && code < 58) && // numeric (0-9)
			!(code > 64 && code < 91) && // upper alpha (A-Z)
			!(code > 96 && code < 123)) { // lower alpha (a-z)
		  return false;
		}
	  }
	return true;
}
