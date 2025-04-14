import { Assert } from 'test/assert';

var db = $.db;
var connection = db.getConnection();

Assert.assertTrue(connection != null && connection != undefined);