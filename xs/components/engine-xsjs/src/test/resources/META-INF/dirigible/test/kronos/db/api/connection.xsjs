import { Assert } from 'test/assert';

var db = $.db;
// tests isClosed() too
function close(){
  var connection = db.getConnection();
  var beforeClose = connection.isClosed();
  connection.close();
  var afterClose = connection.isClosed();

  return !beforeClose && afterClose;
}

function prepareCall(){
  var connection = db.getConnection();
  var call = connection.prepareCall("CREATE TABLE TEST (ID int)");
  connection.close();
  call.close();
  return call.constructor.name == "XscCallableStatement"
}

function prepareStatement(){
  var connection = db.getConnection();
  var statement = connection.prepareStatement("CREATE TABLE TEST (ID int)");
  connection.close();
  statement.close();
  return statement.constructor.name == "XscPreparedStatement"
}

Assert.assertTrue(close() && prepareCall() && prepareStatement());