var mongodb = require('mongodb')
var MongoClient = mongodb.MongoClient
var url = 'mongodb://localhost:27017/data'
const http = require('http')
const port = 8082
const requestHandler = (request, response) => {
    var body = []
    var res = []
    request.on('data', function(chunk) {
        body.push(chunk)
    }).on('end', function() {
        body = Buffer.concat(body).toString()
        bodyJson = JSON.parse(body)
        MongoClient.connect(url, function(err, db) {
            if (err) {
                console.log('Unable to connect to the mongoDB server. Error:', err)
            } else {
                // data collection
                var dataCollection = db.collection('data')
                dataCollection.insert(bodyJson, function(err, result) {
                        if (err) {
                            console.log(err)
                        }
                    })
                    // usage date 
                console.log(bodyJson['date'].substring(4, 10));
                dateData = bodyJson['date'];
                var usageDateCollection = db.collection('usageDate')
                if (bodyJson.hasOwnProperty('date')) {
                    var dateData = bodyJson['date'].substring(4, 10);
                    usageDateCollection.update({ 'date': dateData }, { '$inc': { 'sequence': 1 } }, { 'upsert': true }, function(err, result) {
                        if (err) {
                            console.log(err)
                        }
                    });

                    // usageDateCollection.insert({ "": "" }
                }
                db.close()
            }
        })
    })
    response.end('Done')
}
const server = http.createServer(requestHandler)
server.listen(port, (err) => {
    if (err) {
        return console.log('something bad happened', err)
    }
    console.log(`server is listening on ${port}`)
})