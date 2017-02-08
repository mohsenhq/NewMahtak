const assert = require('assert');
var expect = require('chai').expect;
var XMLHttpRequest = require("xmlhttprequest").XMLHttpRequest;

const index = require('../routes/index');
describe('CallWebAPI', function() {
    var request = new XMLHttpRequest();
    it('should return -1 when the value is not present', function() {
        request.open('post', '/Build/hi');
        request.setRequestHeader("Authorization", false);
        request.send();
        console.log(request.responseText);
        expect(request.responseText).to.be.ok;
        // assert.equal(4, 4);
    });
});