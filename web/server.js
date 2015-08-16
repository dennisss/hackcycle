#!/usr/bin/node

'use strict';


var express = require('express'),
	mongoose = require('mongoose');

global._ = require('underscore');

var a = require('./util');


var app = express();
var server = require('http').Server(app);




mongoose.connect('mongodb://localhost/cycle');


var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function(callback){



	require('./socket')(server);


	app.use(express.static(__dirname + '/public'));


	server.listen(8080, function(err){
		console.log("Server running...")
	});



});
