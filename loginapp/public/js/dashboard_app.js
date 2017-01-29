// log given argument
function log(logData) {
    console.log(logData);
}
// new Dashboard
rf.StandaloneDashboard(function(db) {
    var form = new FormComponent();
    form.setDimensions(3, 4);
    form.addDateRangeField("data_period", "Data Period");

    form.onApplyClick(function(params) {
        log(form.getInputValue("data_period"));
        log(params);
    });

    db.addComponent(form);

    // Dashboard title
    db.setDashboardTitle("APP Dashboard");
    // create new Chart and locks it
    var chart = new ChartComponent();
    chart.setCaption("All Users vs new Installs");
    chart.setDimensions(9, 6);
    chart.setYAxis('Number of Users');
    chart.lock();
    db.addComponent(chart);

    // create new Kpi and locks it  
    var kpi = new KPIComponent();
    kpi.setDimensions(3, 2);
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
                    seriesDisplayType: 'line'
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
    var chart2 = new ChartComponent();
    chart2.setCaption("Daily use of App");
    chart2.setDimensions(9, 6);
    chart2.setYAxis('launches Number');
    chart2.lock();
    db.addComponent(chart2);

    $.ajax({
        url: '/usageDate',
        type: 'POST',
        data: '',
        contentType: 'application/json; charset-utf-8',
        dataType: 'json',
        success: function(data) {
            log(data);
            if (data.dates.length < 31) {
                chart2.setLabels(data.dates);
                chart2.addSeries('times', 'Times', data.sequences, {
                    seriesDisplayType: 'line'
                });
            } else {
                chart2.setLabels(data.dates);
                chart2.addSeries('times', 'Times', data.sequences.slice(0, 30), {
                    seriesDisplayType: 'line'
                });
            }
            // unlocks the chart
            chart2.unlock();
        },
        error: function(xhr, status, error) {
            console.log(error);
        }
    });
});

// StandaloneDashboard(function(db) {
//     var chart = new ChartComponent();
//     // chart.setOption('showLegendFlag', false);
//     chart.setCaption("Country wide sales");
//     chart.setLabels(["Country A", "Country B", "Country C"]);
//     chart.addSeries("sales", "Sales", [10, 7, 11])
//     db.addComponent(chart);
//     chart.addDrillStep(function(done, params) {
//         chart.setLabels(["State A", "State B"])
//         chart.addSeries("sales", "Sales", [4, 3])

//         done(); // This is required
//     });

//     chart.addDrillStep(function(done, params) {
//         chart.setLabels(["City A", "City B", "City C", "City D"])
//         chart.addSeries("sales", "Sales", [3, 1, 4, 2])

//         done(); // This is required
//     });
// });

// rf.StandaloneDashboard(function(db) {
//     var sourceChart = new ChartComponent();
//     sourceChart.setDimensions(4, 4);
//     sourceChart.setCaption("2011 Sales");
//     sourceChart.setLabels(["Beverages", "Vegetables"])
//     sourceChart.addSeries("sales", "Sales", [1343, 7741]);
//     sourceChart.addSeries("quantity", "Quantity", [76, 119]);
//     db.addComponent(sourceChart);
//     var targetChart = new ChartComponent();
//     targetChart.hideComponent();
//     db.addComponent(targetChart);

//     sourceChart.onItemClick(function(params) {
//         targetChart.lock();
//         targetChart.setCaption("Zone-wise breakdown of " + params.label);
//         // You can form/process the data as required.
//         targetChart.setLabels(["North Zone", "South Zone"]);
//         targetChart.addSeries("sales", "Sales", [21, 46]);
//         targetChart.showAsModal();
//         targetChart.unlock();
//     });
// });

// rf.StandaloneDashboard(function(db) {
//     var chart = new ChartComponent();
//     chart.setDimensions(4, 4);
//     chart.setCaption("Stacked Column Chart");
//     chart.setLabels(["Jan", "Feb", "Mar"]);
//     chart.setYAxis("", {
//         numberPrefix: "$"
//     });
//     chart.addSeries("beverages", "Beverages", [1355, 1916, 1150], {
//         seriesStacked: true,
//         seriesDisplayType: "column"
//     });
//     chart.addSeries("packaged_foods", "Packaged Foods", [1513, 976, 1321], {
//         seriesStacked: true,
//         seriesDisplayType: "column"
//     });
//     chart.addSeries("vegetables", "Vegetables", [1313, 1976, 924], {
//         seriesStacked: true,
//         seriesDisplayType: "column"
//     });
//     db.addComponent(chart);
// });