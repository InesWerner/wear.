
//----------------------------------------------------
// Ports
//----------------------------------------------------
var webSocketPort = 4001;	// Zum Austausch von Meldungen und Kommandos
var webServerPort = 4000;	// Zum Laden der HRML-Seite


//----------------------------------------------------
// Our WebSocket Server
//----------------------------------------------------
var WebSocketServer = require('ws').Server
var wss = new WebSocketServer({port: webSocketPort});

console.log('WebSocket-Server listening on port ', webSocketPort, '.');
wss.on('connection', ((ws) => {
	
	ws.on('message', (message) => {
		console.log('receive:', message);
		
		try {
			var json = JSON.parse(message);
			
			// handle incoming message
			if (json.type === 'message') {
				
				messageBack = JSON.stringify({ type: 'message', data: 'Hello Client'} );
				ws.send(messageBack);
				
				
			} else if (json.type === 'command') {
				// handle incoming command
				
				if (json.data === 'getTemp') {
					// Hier koennte man nun die Kommandos zum Auslesen einer Temperatur etc. abarbeiten und Werte zurueckgeben
				
					messageBack = JSON.stringify({ type: 'temp', data: '20.3'} );
				} else {
					
					messageBack = JSON.stringify({ type: 'error', data: 'wrong command'} );
				}
				
				ws.send(messageBack);
			}
			console.log('send:', messageBack);
			
			
		} catch (e) {
			console.log('This is not a valid JSON command: ', message);
			
			// Hier noch besseres Error-Handling	
			
		}
		
	});

	ws.on('end', () => {
		console.log('Connection ended...');
	});
	
}));


//----------------------------------------------------
// Our Web Server
//----------------------------------------------------
var express = require('express');
var websrv = express();
var path = require('path');

// Request to root /
websrv.get('/', function (req, res) {
    // res.send('<b>My</b> first express http server');
	res.sendFile(path.join(__dirname + '/web/clnt.html'));
});

// Request to /welcome
websrv.get('/welcome', function (req, res) {
    res.send('<b>Welcome Emily</b>');
});

// Error 404 message
websrv.use(function(req, res, next) {
    res.status(404).send("Sorry Emily, this webpage is not valid:-)");
});

// Start the server at the port xxxx
websrv.listen(webServerPort, function () {
    console.log('Web-Server listening on port ', webServerPort, '.');
});


