var path;
$(document).ready(function () {
    path = window.location.pathname;
    setInterval(fetchComments(), 10000);
});

function fetchComments() {
    var url = path.split("/");
    var img = url[3];
    $.ajax({
        type: "GET",
        url: "/150020500/Comments/" + img,
        async: true,
        cache: false,
        timeout: 10000,
        success: function (data) {
            console.log(data);
            parseComments(data);
        }, //end success
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus + " : " + errorThrown);
        }//end error
    });//end AJAX
}//end fetchComments()

function parseComments(data) {
    $("#allComments").empty();
    $(data).each(function (index, item) {
        $("#allComments").append("<li>" + item.user + ": " + item.comment + "</li>");
    });
}//end parseComments()