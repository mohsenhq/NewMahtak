var express = require('express');
var path = require('path');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var btoa = require('btoa');
var router = express.Router();
var Application = require('../models/application');

var mongodb = require('mongodb');
var MongoClient = mongodb.MongoClient;

router.get('/', ensureAuthenticated, function(req, res) {
    Application.viewTable(res.locals.user.username, function(err, application) {
        if (err) throw err;
        res.render('applications', { apps: application });
    });
});
router.get('/addApp', ensureAuthenticated, function(req, res) {
    res.render('index');
});

router.get('/Build/*', ensureAuthenticated, function(req, res) {
    CallWebAPI(req.params[0]);
    res.redirect('/');
});

router.get('/MahtakDashboard', ensureAuthenticated, function(req, res) {
    // CallWebAPI(req.params[0]);
    // res.redirect(path.join(__dirname, '../public', 'index2.html'));
    res.sendFile(path.join(__dirname, '../public', 'index2.html'));
});

router.post('/query', ensureAuthenticated, function(req, res) {
    console.log("test2");
    var oooo = [];
    MongoClient.connect('mongodb://localhost:27017/data', function(err, db) {
        if (err) {
            console.log('Unable to connect to the mongoDB server. Error:', err)
        } else {
            var collection = db.collection('data');
            collection.find({}).sort({ _id: -1 }).limit(10).toArray(function(err, results) {
                oooo = results;
                console.log(results);
                res.write(JSON.stringify(results));
                res.end();

            });
        }
    });

    // res.write(JSON.stringify(oooo));
    // res.end();
});

function authenticateUser(user, password) {
    var token = user + ":" + password;

    // Should i be encoding this value????? does it matter???
    // Base64 Encoding -> btoa
    var hash = btoa(token);

    return "Basic " + hash;
};



function ensureAuthenticated(req, res, next) {
    if (req.isAuthenticated()) {
        return next();
    } else {
        // req.flash('error_msg','You are not logged in');
        res.redirect('/users/login');
    }
}

function CallWebAPI(a) {
    var username = a;
    var request = new XMLHttpRequest();
    request.open("POST", "http://198.143.180.135:8080/job/Module/buildWithParameters?token=mohsen&AAR=aarName=" + username, false);
    request.setRequestHeader("Authorization", authenticateUser('mohsenhq', 'Mohsenhq102w.hq'));
    request.send();
};

module.exports = router;