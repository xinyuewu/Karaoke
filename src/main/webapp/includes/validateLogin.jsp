<%
if(session.getAttribute("LoggedIn") == null)
    response.sendRedirect("login.jsp");
%>