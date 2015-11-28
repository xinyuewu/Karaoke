<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.LoggedIn"%>
<%
 LoggedIn l = (LoggedIn) session.getAttribute("LoggedIn");
 if(l == null)
     response.sendRedirect("/Karaoke/login.jsp");
%>