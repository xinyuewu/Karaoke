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
            <%java.util.LinkedList<Track> tracks = (java.util.LinkedList<Track>) request.getAttribute("tracks");%>
            <div class="track-list">
                <h2 class="form-login-heading">Select A Track</h2>
                <div class="track-wrap">
                    <%for (Track t : tracks) {
                    %>
                    <a href="/Karaoke/Music/<%=t.getSUUID()%>"><%=t.getName()%></a><br />
                    <%}%>
                </div>          
            </div>	  
        </section>
    </body>
</html>
