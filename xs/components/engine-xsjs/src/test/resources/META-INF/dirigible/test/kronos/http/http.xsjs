import { Assert } from 'test/assert';

let http = $.net.http;
let client = new http.Client();
let request = new http.Request(http.GET, "/");
let destination = http.readDestination("test", "test-destination");

client.setTimeout(10);

client.request(request, destination);
let response = client.getResponse();

Assert.assertTrue(response.status === http.OK && response.cookies != null && response.headers != null && response.body != null);
