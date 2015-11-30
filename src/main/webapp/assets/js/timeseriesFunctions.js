var path;
var v;
$(document).ready(function () {
    $('.track-list input').on('change', function () {
        setValue();
    });
});

function setValue() {
    v = $('input:checked', '.track-list').val();
    $.ajax({
        type: "GET",
        url: "/Karaoke/Play/" + v,
        async: true,
        cache: false,
        timeout: 10000,
        success: function (data) {
            if (data.length > 0) {
                renderChart(data);
            }
            else {
                $("#chartContainer").html("Unable to display data as the track has no plays");
            }

        }, //end success
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus + " : " + errorThrown);
        }//end error
    });//end AJAX
}

function renderChart(data) {
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