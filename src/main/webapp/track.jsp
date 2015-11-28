<%@page import="java.util.Set"%>
<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.Track"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="includes/validateLogin.jsp"%>
    <head>
        <%@include file="includes/head.jsp"%>
        <title>All tracks</title>
    </head>
    <body>
        <section class="container">
            <%@include file="includes/header.jsp"%>
            <% Track t = (Track) request.getAttribute("track");
            %>
            <div class="audio-container">
                <h2 class="audio-heading"><%=t.getName()%></h2>
                <audio controls>
                    <source src="/Karaoke/Fetch/<%=t.getSUUID()%>" type="audio/mp3">
                    You shouldn't see this message.
                </audio>
                <div class="like">
                    <button onclick="performLike();">Like</button>    
                    <div class="likes"></div>
                </div>
            </div>
        </section>
        <script src="/Karaoke/assets/js/jquery.js"></script>
        <script src="/Karaoke/assets/js/trackFunctions.js"></script>
    </body>
</html>
