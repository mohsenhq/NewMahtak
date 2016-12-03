var mongoose = require('mongoose');

// Application Schema
var ApplicationSchema = mongoose.Schema({
	appName: {
		type: String
	},
	companyDomain: {
		type: String
	},
	appVersion: {
		type: String
	}
});

var Application = module.exports = mongoose.model('Application',ApplicationSchema);

module.exports.addApplication = function(newApplication,callback){
	
	    	newApplication.save(callback);
	    
	};


module.exports.getUserByAppName = function(appName, callback){
	var query = {appName: appName};
	User.findOne(query, callback);
};
