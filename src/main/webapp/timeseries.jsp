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
         
              
            <div id="curve_chart"  style="width: 300px; height: 50px;   "></div>
          
            <div id="chartContainer" style=" text-align: center; "></div>
              
            <script src="/Karaoke/assets/js/jquery.js"></script>
            <script  src="/Karaoke/assets/fusionchart/fusioncharts.js"></script>
            <script src="/Karaoke/assets/fusionchart/fusioncharts.charts.js"></script>
            <script src="/Karaoke/assets/fusionchart/themes/fusioncharts.theme.zune.js"></script>
            <script src="/Karaoke/assets/js/app.js"></script>
            <script src="/Karaoke/assets/js/timeseriesFunctions.js"></script>
            <br>
        </section>
            <script src="assets/js/jquery.js"></script>
            <script src="assets/js/bootstrap.min.js"></script>
            <script type="text/javascript" src="assets/js/jquery.backstretch.min.js"></script>
            <script>
               $.backstretch("assets/img/karaheader.png", {speed: 500});
            </script>
                <br>
                <%//@include file="footer.jsp"%>   
    </body>
</html>
