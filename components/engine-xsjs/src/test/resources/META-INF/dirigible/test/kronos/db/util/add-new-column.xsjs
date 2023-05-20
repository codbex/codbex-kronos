var database = require('kronos/db');
var response = require('http/response');

var tablename = "TEST_USERS";
var newColumnDefinition = "(AGE TINYINT UNSIGNED)";
var sql = "ALTER TABLE " + tablename + " ADD " + newColumnDefinition;

try {
	var connection = database.getConnection();
   	var statement = connection.prepareStatement(sql);
   	var result = statement.executeUpdate();
   	response.println("Query OK, " + result + " rows affected");
	statement.close();
} catch(e) {
   console.trace(e);
   response.println(e.message);
} finally {
   connection.close();
}

response.flush();
response.close();