var db = $.db;
var assertTrue = require('test/assert').assertTrue;
var connection = db.getConnection();

assertTrue(connection != null && connection != undefined);