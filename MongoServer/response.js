var mongodb = require('mongodb')
var MongoClient = mongodb.MongoClient
var url = 'mongodb://mohsenhq:Mohsenhq102@localhost:27017/data?authMechanism=DEFAULT&authSource=admin'
const http = require('http')
const port = 8081
const requestHandler = (request, response) => {
    var body = []
    var res = []
    MongoClient.connect(url, function(err, db) {
        if (err) {
            console.log('Unable to connect to the mongoDB server. Error:', err)
        } else {
            var collection = db.collection('data')
            collection.find({}).sort({ _id: -1 }).limit(1).toArray(function(err, results) {
                res = JSON.stringify(results)
                console.log(res)
                response.end(res)
            })
        }
    })
}
const server = http.createServer(requestHandler)
server.listen(port, (err) => {
    if (err) {
        return console.log('something bad happened', err)
    }
    console.log(`server is listening on ${port}`)
})