'use strict';

/**
 * Module dependencies.
 */
var app = require('./config/lib/app');
var server = app.start();
var Mahtak = mongoose.model();

app.post('/', function(req,res,next){
  var u = new Mahtak(req.body)
  db('Mahtak').save(u)
  res.send(200)
});
