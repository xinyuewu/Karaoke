<%@page import="com.mycompany.groupproject.Stores.Track"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            java.util.LinkedList<Track> tracks = (java.util.LinkedList<Track>) request.getAttribute("tracks");
            for (Track t : tracks) {
        %><a href="/GroupProject/Music/<%=t.getSUUID()%>"><%=t.getName()%></a><br /><%
                    }
        %>
    </body>
</html>
