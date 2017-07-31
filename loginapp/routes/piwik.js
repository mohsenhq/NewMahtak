var express = require('express');
var path = require('path');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var btoa = require('btoa');
var router = express.Router();
var Application = require('../models/application');

var mongodb = require('mongodb');
var MongoClient = mongodb.MongoClient;

/**
 * checks if request is from logged in user
 * if not returns to login page
 */
function ensureAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
        return next();
    } else {
        res.redirect('/users/login');
    }
}

router.post('/piwikAPI', ensureAuthenticated, function(req, res) {
    // MongoClient.connect('mongodb://mohsenhq:Mohsenhq102@localhost:27017/data?authMechanism=DEFAULT&authSource=admin', function(err, db) {
    //     // if (err) {
    //     //     console.log('Unable to connect to the mongoDB server. Error:', err)
    //     // } else {
    //     //     // var collection = db.collection('customEvent');
    //     //     // collection.find({
    //     //     //     "APP": currentApp
    //     //     // }, {
    //     //     //     _id: 0,
    //     //     //     custom: 1
    //     //     // }).toArray(function(err, results) {
    //     //     //     res.write(JSON.stringify(results[0].custom));
    //     //     //     res.end();
    //     //     // });
    //     // }
    // });

    var method = "SitesManager.addSite";
    var siteName = req.body.siteName;
    var siteUrls = req.body.urls;
    var request = new XMLHttpRequest();
    var url = "http://localhost/piwik/?module=API&method=" + method + "&siteName=" + siteName + "&urls=" + siteUrls + "&token_auth=fbfbd1c44a838aa7afc3bb9cf2d3aece";
    request.open("get", url);
    request.send();

    console.log(req.body.urls);
    res.write(JSON.stringify(""));
    res.end();
});


module.exports = router;