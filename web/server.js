#!/usr/bin/node

'use strict';


var express = require('express');



var app = express();
var server = require('http').Server(app);


app.use(express.static(__dirname));


server.listen(8000, function(err){
    console.log("Server running...")
});
