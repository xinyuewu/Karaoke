<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.LoggedIn" %>
<% LoggedIn ln = (LoggedIn) session.getAttribute("LoggedIn");
    ln.setLoggedOut();
    session.setAttribute("LoggedIn", null);
    response.sendRedirect("/Karaoke/");
%>