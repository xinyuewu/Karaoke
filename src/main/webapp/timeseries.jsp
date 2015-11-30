<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.Track"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <%@include file="includes/head.jsp"%>
        <title>JSP Page</title>
    </head>
    <body>
        <%java.util.LinkedList<Track> topTracks = (java.util.LinkedList<Track>) request.getAttribute("tracks");%>
        <section class="container">
            <%@include file="includes/header.jsp"%>
            <form class="track-list">
                <h2 class="form-login-heading">Top tracks</h2>
                <div class="track-wrap">
                    <%for (Track t : topTracks) {
                    %>
                    <input type="radio" name="radioGroup" value="<%=t.getSUUID()%>" /> <%=t.getName()%> 
                    Likes: <%=t.getLikes().getTotalLikes()%><br />
                    <%}%>
                </div>          
            </form>

            <div id="curve_chart" style="width: 900px; height: 500px"></div>
            <div id="chartContainer"></div>
            <script src="/Karaoke/assets/js/jquery.js"></script>
            <script  src="/Karaoke/assets/fusionchart/fusioncharts.js"></script>
            <script src="/Karaoke/assets/fusionchart/fusioncharts.charts.js"></script>
            <script src="/Karaoke/assets/fusionchart/themes/fusioncharts.theme.zune.js"></script>
            <script src="/Karaoke/assets/js/app.js"></script>
            <script src="/Karaoke/assets/js/timeseriesFunctions.js"></script>
        </section>
    </body>
</html>
