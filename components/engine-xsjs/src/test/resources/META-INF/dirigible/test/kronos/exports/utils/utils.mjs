import { query } from '@dirigible/db'

var assert = require('test/assert');

export function assertEquals(expected, actual, message) {
  assert.assertEquals(expected, actual, message
   + "\nExpected: " + expected
   + "\nActual: " + actual);
}

export function fetchAllEntriesInTable(stateTableParams) {
  let sql = "SELECT * FROM " + stateTableParams.name;
  return query.execute(sql, null, "local", "SystemDB");
}