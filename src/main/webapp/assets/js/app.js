$('#getdata').click(function () {
    $.ajax({
        url: 'http://localhost:8080/Karaoke/Play/cc9fe060-95e4-11e5-b917-0a0027000000',
        type: 'GET',
        success: function (data) {
            chartData = data;

            var chartProperties = {
                "caption": "Music Trend",
                "xAxisName": "Date",
                "yAxisName": "Times Played",
                "rotatevalues": "1",
                "theme": "zune"
            };
            apiChart = new FusionCharts({
                type: 'line',
                renderAt: 'chartContainer',
                width: '550',
                height: '350',
                dataFormat: 'json',
                dataSource: {
                    "chart": chartProperties,
                    "data": jQuery.parseJSON(chartData)
                }
            });
            apiChart.render();
        }
    });
});

