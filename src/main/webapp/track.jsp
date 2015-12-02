<%@page import="java.util.Set"%>
<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.Track"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="includes/validateLogin.jsp"%>
    <head>
        <%@include file="includes/head.jsp"%>
        <title>All tracks</title>
        <script>
            function addPlay(track) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function () {
                    if (xhttp.readyState === 4 && xhttp.status === 200) {
                    }
                };
                url = "http://localhost:8080/Karaoke/Play";
                xhttp.open("POST", url, true);
                xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
                var dataRequestObject = "track=" + track;

                xhttp.send(dataRequestObject);
            }
        </script>      
    </head>
    <body>
        <section class="container">
            <%@include file="includes/header.jsp"%>
            <% Track t = (Track) request.getAttribute("track");
            %>
            <div class="audio-container">
                <h2 class="audio-heading"><%=t.getName()%></h2>
                <audio controls id="myAudio" preload="auto">
                    <source src="/Karaoke/Fetch/<%=t.getSUUID()%>" type="audio/mp3">
                    You shouldn't see this message.
                </audio>
                <script>
                    var played = 0;
                    var audio = document.getElementById("myAudio");
                    audio.onplay = function () {
                        if (played == 0)
                            addPlay("<%=t.getSUUID()%>");
                        played++;

                    };
                </script> 
                <div class="like">
                    <button onclick="performLike();">Like</button>    
                    <div class="likes"></div>
                </div>
            </div>
        </section>
        <script src="/Karaoke/assets/js/jquery.js"></script>
        <script src="/Karaoke/assets/js/trackFunctions.js"></script>
        <script src="/Karaoke/assets/js/jquery.js"></script>
        <script src="/Karaoke/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/Karaoke/assets/js/jquery.backstretch.min.js"></script>
        <script>
               $.backstretch("/Karaoke/assets/img/karaheader.png", {speed: 500});
        </script>
    </body>
</html>
