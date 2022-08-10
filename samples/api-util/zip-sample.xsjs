// Create a new zip object
var zip = new $.util.Zip();
// Set content to the zip object
zip['kronos.txt'] = 'This is Kronos';

// Download the zip file
$.response.status = $.net.http.OK;
$.response.contentType = 'application/zip';
$.response.headers.set('Content-Disposition', 'attachment; filename = test.zip');
$.response.setBody(zip.asArrayBuffer());