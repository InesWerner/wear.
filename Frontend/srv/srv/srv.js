//____________________________________________________
//Database Connection
//____________________________________________________

var mysql = require('mysql');

var con = mysql.createConnection({
 host: "192.168.2.112",    // BEACHTE: "#mySQL-Einrichtung.txt"
 port: 3306,
 user: "root",
 password: "Sunny!1996"
});

con.connect(function(err) {
 if (err) throw err;

 console.log("Connected!");
}); 

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
				
				console.log('send:', messageBack);
				
			} else if (json.command === 'command') {
				// handle incoming command
				
				if (json.data === 'getTemp') {
					// Hier koennte man nun die Kommandos zum Auslesen einer Temperatur etc. abarbeiten und Werte zurueckgeben
				
					messageBack = JSON.stringify({ command: 'temp', data: '20.3'} );
				} else {
					
					messageBack = JSON.stringify({ command: 'error', data: 'wrong command'} );
				}
				
				ws.send(messageBack);
				
				console.log('send:', messageBack);
				
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
				
				console.log('send:', messageBack);
				
			} else if (json.command === 'getInWardrobe') {
				//------------------------------------------------------------------------------
				// Get data from database and prepare for transmitting 
				// to client via  websocket connection
				//------------------------------------------------------------------------------
				doRefreshFromDB();
				
			} else if (json.command === 'getClothData') {
				//------------------------------------------------------------------------------
				// Get data from database and prepare for transmitting 
				// to client via  websocket connection for one cloth
				//------------------------------------------------------------------------------
				doGetArtFromDB(json.data);
				
			} else if (json.command === 'getRFID') {
				//------------------------------------------------------------------------------
				// Get data from database and prepare for transmitting 
				// to client via  websocket connection
				//------------------------------------------------------------------------------
				doRefreshRFIDFromDB();



			} else if (json.command === 'createItem') {
				//------------------------------------------------------------------------------
				// SQL Statement to save new data in database
				// refresh to show the new item in the InWardrobe table
				//------------------------------------------------------------------------------

				var clothData = JSON.parse(json.data);
				doInsertIntoDB(clothData);
				
				
			} else if (json.command === 'modifyItem') {
				//------------------------------------------------------------------------------
				// SQL Statement to save modified data in database
				// refresh to show the modified item in the InWardrobe table
				//------------------------------------------------------------------------------

				var clothData = JSON.parse(json.data);
				doModifyIntoDB(clothData);
				
				
			} else if (json.command === 'deleteInWardrobe') {
				//------------------------------------------------------------------------------
				// Delete cloth data from database 
				//------------------------------------------------------------------------------		
				artNr = json.data;

				doDeleteFromDB(artNr);

			}
			
			
		} catch (e) {
		
			console.log('This is not a valid JSON command: ', message, e);
			
			// Hier noch besseres Error-Handling	
			
		}
		
	});

	// ws.on('end', () => {
	ws.on('end', function() {
		console.log('Connection ended...');
	});
	
	
	function doDeleteFromDB(ArtNr) {
		//**** Keep in mind that the database commands are asynchronous - see https://stackoverflow.com/questions/15635791/nodejs-mysql-connection-query-return-value-to-function-call
		//------------------------------------------------------------------------------
		// Delete from database
		//------------------------------------------------------------------------------

		var sql = "DELETE FROM IoT_Project_Wardrobe.Item WHERE itemId="+ArtNr;
		
		con.query(sql, function (err, result) {			
			if (err) throw err;
			
			doRefreshFromDB();
			
			messageBack = "Delete article: "+artNr;
				
			console.log('send:', messageBack);
			
		});
		
	}
	

	function doModifyIntoDB(clothData) {
		//**** Keep in mind that the database commands are asynchronous - see https://stackoverflow.com/questions/15635791/nodejs-mysql-connection-query-return-value-to-function-call
		//------------------------------------------------------------------------------
		// Change data into database
		//------------------------------------------------------------------------------
		if (clothData.numberofusage == "")  clothData.numberofusage = 0;
		
		var sql = "UPDATE IoT_Project_Wardrobe.Item SET description='"+clothData.description+"', category='"+clothData.category+"',size='"+clothData.size+"',color='"+clothData.color+"',numberofusage="+clothData.numberofusage+" WHERE itemId="+clothData.artNr;
		
		// console.log(sql);
		
		console.log(clothData);
		
		con.query(sql, function (err, result) {			
			if (err) throw err;
			
			doRefreshFromDB();
			
			console.log("Record modified");
			
		});
		
	}
	
	function doInsertIntoDB(clothData) {
		//**** Keep in mind that the database commands are asynchronous - see https://stackoverflow.com/questions/15635791/nodejs-mysql-connection-query-return-value-to-function-call
		//------------------------------------------------------------------------------
		// Insert data into database
		//------------------------------------------------------------------------------
		if (clothData.numberofusage == "")  clothData.numberofusage = 0;
		
		var sql = "INSERT INTO IoT_Project_Wardrobe.Item (description, category, size, color, numberofusage, tagID) VALUES ('"+clothData.description+"','"+clothData.category+"','"+clothData.size+"','"+clothData.color+"','"+clothData.numberofusage+"','"+clothData.tagID+"')";
		
		con.query(sql, function (err, result) {			
			if (err) throw err;
			
			doRefreshFromDB();
			
			console.log("Record inserted");
			
		});
		
	}
	
	
	
	function doGetArtFromDB(artNr) {
		//**** Keep in mind that the database commands are asynchronous - see https://stackoverflow.com/questions/15635791/nodejs-mysql-connection-query-return-value-to-function-call
		//------------------------------------------------------------------------------
		// Get data from database and prepare for transmitting 
		// to client via  websocket connection for one cloth
		//------------------------------------------------------------------------------
		
		// Gute Data Frame Implementierungen in JavaScript gibt es nicht
		//    siehe https://www.man.com/maninstitute/short-review-of-dataframes-in-javascript

		var cloth = "";
		var clothes = [];
		
		var sql = 'SELECT * FROM IoT_Project_Wardrobe.Item where IoT_Project_Wardrobe.Item.itemId = '+artNr;	
		con.query(sql, function (err, result) {
			if (err) throw err;
			
			var buildJSon="";			
			result.forEach(function(tmpCloth){
						
				tmpJSon =   JSON.stringify({ id: tmpCloth.itemId, 
											 description: tmpCloth.description, 
											 category: tmpCloth.category, 
											 size: tmpCloth.size, 
											 color: tmpCloth.color, 	
											 status: tmpCloth.status, 
											 tagID: tmpCloth.tagID,
											 numUsage: tmpCloth.numberOfUsage} );
				
				// Noch nicht verwendet: `name` varchar(45) DEFAULT NULL,

				if (buildJSon.length > 0) {
					buildJSon = buildJSon + "," + tmpJSon;
				} else {
					buildJSon = tmpJSon;
				}
				
			});
			buildJSon = "[" + buildJSon + "]";
			messageBack = JSON.stringify({ command: 'dataInCloth', data: buildJSon });   
		
			ws.send(messageBack);

			console.log('send:', messageBack);
		});

	}
	
	function doRefreshFromDB() {
		//**** Keep in mind that the database commands are asynchronous - see https://stackoverflow.com/questions/15635791/nodejs-mysql-connection-query-return-value-to-function-call
		//------------------------------------------------------------------------------
		// Get data from database and prepare for transmitting 
		// to client via  websocket connection
		//------------------------------------------------------------------------------
		
		// Gute Data Frame Implementierungen in JavaScript gibt es nicht
		//    siehe https://www.man.com/maninstitute/short-review-of-dataframes-in-javascript

		var cloth = "";
		var clothes = [];
		
		var sql = 'SELECT * FROM IoT_Project_Wardrobe.Item ';
		con.query(sql, function (err, result) {
			if (err) throw err;
			
			var buildJSon="";			
			result.forEach(function(tmpCloth){
						
				tmpJSon =   JSON.stringify({ id: tmpCloth.itemId, 
											 description: tmpCloth.description, 
											 category: tmpCloth.category, 
											 size: tmpCloth.size, 
											 color: tmpCloth.color, 	
											 status: tmpCloth.status, 
											 numUsage: tmpCloth.numberOfUsage} );
				
				// Noch nicht verwendet: `name` varchar(45) DEFAULT NULL,

				if (buildJSon.length > 0) {
					buildJSon = buildJSon + "," + tmpJSon;
				} else {
					buildJSon = tmpJSon;
				}
				
			});
			buildJSon = "[" + buildJSon + "]";
			messageBack = JSON.stringify({ command: 'dataInWardrobe', data: buildJSon });   
		
			ws.send(messageBack);

			console.log('send:', messageBack);
		});

	}
	
	function doRefreshRFIDFromDB() {
		//**** Keep in mind that the database commands are asynchronous - see https://stackoverflow.com/questions/15635791/nodejs-mysql-connection-query-return-value-to-function-call
		//------------------------------------------------------------------------------
		// Get data from database and prepare for transmitting 
		// to client via  websocket connection
		//------------------------------------------------------------------------------
		
		// Gute Data Frame Implementierungen in JavaScript gibt es nicht
		//    siehe https://www.man.com/maninstitute/short-review-of-dataframes-in-javascript

		var cloth = "";
		var clothes = [];
		
		var sql = 'SELECT IoT_Project_Wardrobe.Tag.tagID, IoT_Project_Wardrobe.Tag.tagKey FROM IoT_Project_Wardrobe.Tag WHERE IoT_Project_Wardrobe.Tag.tagID NOT IN ( SELECT IoT_Project_Wardrobe.Item.tagID FROM IoT_Project_Wardrobe.Item )';
		con.query(sql, function (err, result) {
			if (err) throw err;
			
			var buildJSon="";			
			result.forEach(function(tmpRFID){
						
				tmpJSon =   JSON.stringify({ tagID: tmpRFID.tagID, 
											 tagKey: tmpRFID.tagKey} );

				if (buildJSon.length > 0) {
					buildJSon = buildJSon + "," + tmpJSon;
				} else {
					buildJSon = tmpJSon;
				}
				
			});
			buildJSon = "[" + buildJSon + "]";
			messageBack = JSON.stringify({ command: 'dataRFID', data: buildJSon });   
		
			ws.send(messageBack);

			console.log('send:', messageBack);
		});

	}
	
	
	
	function Dummy() {
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
		// clothes[i] = cloth;
		
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
		// clothes[i] = cloth;
		
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
		// clothes[i] = cloth;
		
		i = i+1;
		cloth = {
			id: "ArtNr7",
			description: "asdasjd asdghjags dgasdgjhags dhjgasjdg jhasdhj gadg jhdh gasgdjhasd",
			category: 10,
			size: "S",
			color: "green",
			status: false,
			numUsage: 40
		};
		// clothes[i] = cloth;
	}
	
});


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


