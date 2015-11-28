var path;
$(document).ready(function () {
    path = window.location.pathname;
    setInterval(getLikes, 1000);
});

function performLike(){
     var url = path.split("/");
    var track = url[3];

    $.ajax({
        type: "POST",
        url: "/Karaoke/Like/" + track,
        async: true,
        cache: false,
        timeout: 10000,
        success: function (data) {
            console.log(data);
        }, //end success
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus + " : " + errorThrown);
        }//end error
    });//end AJAX
}

function getLikes(){
     var url = path.split("/");
    var track = url[3];

    $.ajax({
        type: "GET",
        url: "/Karaoke/Like/" + track,
        async: true,
        cache: false,
        timeout: 10000,
        success: function (data) {
            console.log(data);
           $('.likes').html("Likes: "+data);
        }, //end success
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log(textStatus + " : " + errorThrown);
        }//end error
    });//end AJAX
}