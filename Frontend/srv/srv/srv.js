
//----------------------------------------------------
// Ports
//----------------------------------------------------
var webSocketPort = 4001;	// Zum Austausch von Meldungen und Kommandos
var webServerPort = 4000;	// Zum Laden der HRML-Seite

// Server ist multi-threaded (mehrere Devices koennen gleichzeitig daraufzugreifen) etc. - jedoch nur Geruest!!!!

//----------------------------------------------------
// Our WebSocket Server
//----------------------------------------------------
var WebSocketServer = require('ws').Server
var wss = new WebSocketServer({port: webSocketPort});

console.log('WebSocket-Server listening on port ', webSocketPort, '.');

//***********************************************************************************************
// Absicherung der Verbindung mittels Ping-Pong-Verfahren: 
//    https://stackoverflow.com/questions/26971026/handling-connection-loss-with-websockets
//***********************************************************************************************
		
// wss.on('connection', ((ws) => {
wss.on('connection', function(ws) {
	//------------------------------------------------------------------------------
	// Get commands and data from client via websocket connection
	//------------------------------------------------------------------------------
		
	// ws.on('message', (message) => {
	ws.on('message', function(message) {
		console.log('receive:', message);
		
		try {
			var json = JSON.parse(message);
			
			// handle incoming message
			if (json.command === 'message') {
				
				messageBack = JSON.stringify({ command: 'message', data: 'Hello Client'} );
				ws.send(messageBack);
				
				
			} else if (json.command === 'command') {
				// handle incoming command
				
				if (json.data === 'getTemp') {
					// Hier koennte man nun die Kommandos zum Auslesen einer Temperatur etc. abarbeiten und Werte zurueckgeben
				
					messageBack = JSON.stringify({ command: 'temp', data: '20.3'} );
				} else {
					
					messageBack = JSON.stringify({ command: 'error', data: 'wrong command'} );
				}
				
				ws.send(messageBack);
				
			} else if (json.command === 'updateCloth') {
				// handle incoming updateCloth command
				
				var clothData = JSON.parse(json.data);
				
				// read incoming data of cloth
				var clothNr = clothData.cNr;
				var clothName = clothData.cName;
				var clothColor = clothData.cColor;

				// update data into database
				// ...
				

				// send feedback to client
				messageBack = JSON.stringify({ command: 'updateCloth', data: clothName} );
				
				ws.send(messageBack);
				
			} else if (json.command === 'getInWardrobe') {
				//------------------------------------------------------------------------------
				// Get data from database and prepare for transmitting 
				// to client via  websocket connection
				//------------------------------------------------------------------------------
				
				doRefreshFromDB();
				
				
			} else if (json.command === 'deleteInWardrobe') {
				//------------------------------------------------------------------------------
				// Delete cloth data from database 
				//------------------------------------------------------------------------------		
				artNr = json.data;
				
				//********* Delete here Article in Database ***********
				
				
				//*****************************************************
				
				doRefreshFromDB();
				
				messageBack = "Delete article: "+artNr;

			}
			
			console.log('send:', messageBack);
			
			
		} catch (e) {
			console.log('This is not a valid JSON command: ', message);
			
			// Hier noch besseres Error-Handling	
			
		}
		
	});

	// ws.on('end', () => {
	ws.on('end', function() {
		console.log('Connection ended...');
	});
	
	
	function doRefreshFromDB() {
		//------------------------------------------------------------------------------
		// Get data from database and prepare for transmitting 
		// to client via  websocket connection
		//------------------------------------------------------------------------------
		
		// Gute Data Frame Implementierungen in JavaScript gibt es nicht
		//    siehe https://www.man.com/maninstitute/short-review-of-dataframes-in-javascript

		var cloth = "";
		var clothes = [];

		// Dummy data to simulate wardrobe data from database
		i = 1;
		// cloth = {id:"John", description:"Doe", category:50};
		cloth = {
			id: "ArtNr1",
			description: "Doe",
			category: 50,
			size: "L",
			color: "red",
			status: true,
			numUsage: 20
		};
		clothes[i] = cloth;
		
		i = i+1;
		cloth = {
			id: "ArtNr3",
			description: "Test",
			category: 10,
			size: "M",
			color: "green",
			status: true,
			numUsage: 20
		};
		clothes[i] = cloth;
		
		i = i+1;
		cloth = {
			id: "ArtNr4",
			description: "asdgjhgas dhjasdhjgasgdhjasdhj gasgjhjgahjdg",
			category: 10,
			size: "S",
			color: "blue",
			status: false,
			numUsage: 30
		};
		clothes[i] = cloth;
		
		i = i+1;
		cloth = {
			id: "ArtNr7",
			description: "asdasjd asdghjags dgasdgjhags dhjgasjdg jhasdhj gadg jhdh gasgdjhasd",
			category: 10,
			size: "S",
			color: "green",
			status: true,
			numUsage: 40
		};
		clothes[i] = cloth;


		var buildJSon="";
		for (i = 1; i < clothes.length; i++) { 	
			tmpCloth = clothes[i]; 
						
			buildJSon = buildJSon + JSON.stringify({ id: tmpCloth.id, 
											 description: tmpCloth.description, 
											 category: tmpCloth.category, 
											 size: tmpCloth.size, 
											 color: tmpCloth.color, 	
											 status: tmpCloth.status, 
											 numUsage: tmpCloth.numUsage} );
									 
			if (i < (clothes.length-1)) {
				buildJSon = buildJSon + ",";
			}
			
		}		
		buildJSon = "[" + buildJSon + "]";

		messageBack = JSON.stringify({ command: 'dataInWardrobe', data: buildJSon });   
		
		ws.send(messageBack);
	}
	
});


// }));


//----------------------------------------------------
// Our Web Server
//----------------------------------------------------
var express = require('express');
var websrv = express();
var path = require('path');

// Request to root /
websrv.get('/', function (req, res) {
	
    // res.send('<b>My</b> first express http server');
	res.sendFile(path.join(__dirname + '/web/index.html'));    //  '/web/clnt.html'
});

// Request to banner1.png 
websrv.get('/img/banner1.png', function (req, res) {
	
	res.sendFile(path.join(__dirname + '/web/img/banner1.png'));   
});

// Request to style.css 
websrv.get('/css/style.css', function (req, res) {
	
	res.sendFile(path.join(__dirname + '/web/css/style.css'));   
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


