// Zip byte array source
var source = [123, 34, 107, 114, 111, 110, 111, 115, 46, 116, 120, 116, 34, 58, 34, 84, 104, 105, 115, 32, 105, 115, 32, 75, 114, 111, 110, 111, 115, 34, 125];

var zip = new $.util.Zip({
  source: source
});

for (var entry in zip) {
  // Loop through zip entries and modify if needed
  if (entry === 'kronos.txt') {
    zip[entry] = 'Kronos is great'
  }
}

// Download the zip file
$.response.status = $.net.http.OK;
$.response.contentType = 'application/zip';
$.response.headers.set('Content-Disposition', 'attachment; filename = test.zip');
$.response.setBody(zip.asArrayBuffer());