// log given argument
function log(logData) {
    console.log(logData);
}
// new Dashboard
rf.StandaloneDashboard(function(db) {
    // Dashboard title
    db.setDashboardTitle("APP Dashboard");
    // create new Chart and locks it
    var chart = new ChartComponent();
    chart.setCaption("All Users vs new Installs");
    chart.setDimensions(10, 6);
    chart.setYAxis('Number of Users');
    chart.lock();
    db.addComponent(chart);

    // create new Kpi and locks it  
    var kpi = new KPIComponent();
    kpi.setDimensions(2, 2);
    kpi.setCaption("Total users");
    kpi.lock();
    db.addComponent(kpi);

    /**
     * post request to get dailyUsers
     * if success: add dates as chart's Labels and usersNumber as it's Series 
     * set data point limitation to 30
     */
    $.ajax({
        url: '/dailyUsers',
        type: 'POST',
        data: '',
        contentType: 'application/json; charset-utf-8',
        dataType: 'json',
        success: function(data) {
            log(data);
            if (data.dates.length < 31) {
                chart.setLabels(data.dates);
                chart.addSeries('totalUsers', 'Total Users', data.usersNumber, { seriesDisplayType: 'line' });
            } else {
                chart.setLabels(data.dates.slice(0, 30));
                chart.addSeries('totalUsers', 'Total Users', data.usersNumber.slice(0, 30), { seriesDisplayType: 'line' });
            }
        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });

    /**
     * post request to get installDate
     * if success: add newInstalls as chart's Series 
     * set data point limitation to 30
     */
    $.ajax({
        url: '/installDate',
        type: 'POST',
        data: '',
        contentType: 'application/json; charset-utf-8',
        dataType: 'json',
        success: function(data) {
            log(data);
            if (data.dates.length < 31) {
                chart.addSeries('newInstalls', 'New Installs', data.newInstalls, {
                    seriesDisplayType: 'line',
                    yAxis: 'newInstalls'
                });
            } else {
                chart.addSeries('newInstalls', 'New Installs', data.newInstalls.slice(0, 30), {
                    seriesDisplayType: 'line'
                });
            }
            // unlocks the chart
            chart.unlock();

            // set kpi value "total user" equal to the sum of new Installs
            a = 0;
            for (i = 0; i < data.newInstalls.length; i++) {
                a += data.newInstalls[i];
            }
            kpi.setValue(a);
            //unlocks kpi
            kpi.unlock();

        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });
});