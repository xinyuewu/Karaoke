<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.LoggedIn" %>
<% LoggedIn ln = (LoggedIn) session.getAttribute("LoggedIn");
    if (ln != null) {
        ln.setLoggedOut();
        session.setAttribute("LoggedIn", null);
        response.sendRedirect("/Karaoke/");
    }
    else
       response.sendRedirect("/Karaoke/login.jsp");
%>