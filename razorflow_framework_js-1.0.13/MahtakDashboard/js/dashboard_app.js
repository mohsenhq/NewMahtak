var a;
$.ajax("http://localhost:8081/",{
    type: "GET",
      dataType: 'jsonp',
      data: {
      format: 'json'},   
    success: function (data) {
    	a=data.parse(json);
        // rf.StandaloneDashboard(function (db) {
        //     // use the data from your 'data' variable to 
        //     // build the dashboard in the 'db' variable
        // });
        return data;
    }
})
console.log(a);