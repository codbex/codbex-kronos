import { query } from 'sdk/db'
import { Assert } from 'test/assert';

export function assertEquals(expected, actual, message) {
  Assert.assertEquals(expected, actual, message
   + "\nExpected: " + expected
   + "\nActual: " + actual);
}

export function fetchAllEntriesInTable(stateTableParams) {
  let sql = "SELECT * FROM " + stateTableParams.name;
  return query.execute(sql, null, "local", "SystemDB");
}