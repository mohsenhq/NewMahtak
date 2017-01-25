function log(logData) {
    console.log(logData);
}
rf.StandaloneDashboard(function(db) {
    db.setDashboardTitle("APP Dashboard");
    var chart = new ChartComponent();
    chart.setCaption("App Usage and new Installs");
    chart.setDimensions(12, 6);
    chart.setYAxis('Usage');
    chart.addYAxis('newInstalls', "newInstalls");

    chart.lock();
    db.addComponent(chart);

    $.ajax({
        url: '/usageDate',
        type: 'POST',
        data: '',
        contentType: 'application/json; charset-utf-8',
        dataType: 'json',
        success: function(data) {
            log(data);
            chart.setLabels(data.dates);
            chart.addSeries('usage', 'Usage', data.sequences, { seriesDisplayType: 'line' });
        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });

    $.ajax({
        url: '/installDate',
        type: 'POST',
        data: '',
        contentType: 'application/json; charset-utf-8',
        dataType: 'json',
        success: function(data) {
            log(data);
            chart.addSeries('newInstalls', 'New Installs', data.newInstalls, {
                seriesDisplayType: 'line',
                yAxis: 'newInstalls'
            });
            chart.unlock();
        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });
});