var http = require('http');
var port = 9500;
var server = http.createServer(function(request, response) {
    response.setHeader('Content-Type', 'text/html');
    response.end('<h1>Node.js server is running!</h1>');
});
server.listen(port, function() {
    console.log('Node.js server listening on port: ' + port);
});