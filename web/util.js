'use strict';


var ObjectId = require('mongodb').ObjectID

ObjectId.prototype.toString = function(){
	var str = new Buffer(this.id, 'binary').toString('hex')
	str = str.replace(/\+/g, '-').replace(/\//g, '_')
	return str;
}
ObjectId.prototype.toJSON = ObjectId.prototype.toString;



global.parse_id = function(str){
	return new ObjectId(new Buffer(str, 'hex').toString('binary'));
}
