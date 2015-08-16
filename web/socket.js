'use strict';


var fs = require('fs'),
	mkdirp = require('mkdirp'),
	mongoose = require('mongoose'),
	async = require('async');





var Schema = mongoose.Schema;

var TripSchema = new Schema({

	date: { type: Date, default: Date.now }

});
var Trip = mongoose.model('Trip', TripSchema);



var PointSchema = new Schema({

	trip_id: Schema.ObjectId,

	image: String,

	lat: Number,
	lon: Number,

	date: { type: Date, default: Date.now }
});
var Point = mongoose.model('Point', PointSchema);








function save_image(id, image_data, callback){

	var datadir = __dirname + '/public/data';
	mkdirp(datadir, function(err){
		if(err){
			callback(err);
			return;
		}

		var path = datadir + '/' + id.toString() + '.jpg';
		fs.writeFile(path, image_data, {encoding: 'base64'}, function(err){
			callback(err, '/data/' + id.toString() + '.jpg');
		});
	});
}



module.exports = function(server){

    var io = require('socket.io').listen(server); //(server, {
    //    path: '/ws'
    //});


    io.on('connection', function (socket) {

        socket.on('new_trip', function(data){ // Start a new trip

        });


        socket.on('data', function(data){ // Receive position data about the trip


			async.map(data, function(pdata, callback){

				var p = new Point(pdata);
				console.log('adding point ' + p.id);

				if(pdata.image_data){

					save_image(p.id, pdata.image_data, function(err, path){
						console.log(path)
						if(err)
							console.log(err)
						else{
							p.image = path;
							p.save(function(err){
								callback(err, p);
							});
						}
					})
				}
				else{
					p.save(function(err){
						callback(err, p);
					})
				}



			}, function(err, points){

				if(err)
					console.log(err);

				socket.broadcast.emit('data', _.map(points, function(p){
					return {
						id: p.id,
						lat: p.lat,
						lon: p.lon,
						image: p.image
					};
				}));
			})

        });

		socket.on('beat', function(data){
			socket.broadcast.emit('beat', data);
		})


        socket.on('crash', function(data){ // A crash was reported, an email should be sent to an emergency contact


        })


    });



}
