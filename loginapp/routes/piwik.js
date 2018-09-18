var express = require('express');
var path = require('path');
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;
var btoa = require('btoa');
var router = express.Router();
var Application = require('../models/application');
var request = require('request');

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

    var method = "SitesManager.addSite";
    var siteName = req.body.siteName;
    var siteUrls = req.body.urls;
    var group = res.locals.user.username;
    var url = "http://81.31.168.201/analytics/?module=API&method=" + method + "&siteName=" + siteName + "&urls=" + siteUrls + "&group=" + group + "&token_auth=4e8f3fc4f69cd6cb5c0f61af5305e71d";

    request(url, function(error, response, body) {
        res.write(JSON.stringify(body));
        res.end();
    });
});


router.post('/siteList', ensureAuthenticated, function(req, res) {
    var group = res.locals.user.username;
    url = "http://81.31.168.201/analytics/?module=API&method=SitesManager.getSitesFromGroup&group=" + group + "&token_auth=4e8f3fc4f69cd6cb5c0f61af5305e71d&format=JSON";
    request(url, function(error, response, body) {
        res.write(JSON.stringify(body));
        res.end();
    });

});

module.exports = router;