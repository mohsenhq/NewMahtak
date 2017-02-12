var User, app, mongoose, request, expect, user;

expect = require("chai").expect;
app = require("../app");
User = require('../models/user');
var request = require('supertest');

describe('User', function() {
    before(function(done) {
        user = new User({
            name: "test",
            email: "user@user.com",
            username: "test",
            password: "1234qwer"
        });
        User.createUser(user, function(err, user) {
            if (err) throw err;
        });
        done();
    });
    describe('Login test', function() {
        it('should redirect to /', function(done) {
            request(app)
                .post('/users/login')
                .send({ username: 'test', password: '1234qwer' })
                // .field('username', 'test')
                // .field('password', '1234qwer')
                .expect(302, function(err, res) {
                    if (err) throw err;
                    expect(res.header.location).to.equal('/');
                    done();
                });

            after(function(done) {
                User.remove().exec();
                return done();
            });

        })
    })
});