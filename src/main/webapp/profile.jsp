<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.Address"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="uk.ac.dundee.computing.aec.karaoke.stores.Person"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="includes/validateLogin.jsp"%>
    <head>
        <%@include file="includes/head.jsp"%>
        <title>All tracks</title>
    </head>
    <body>
        <%            Person p = (Person) request.getAttribute("person");

            Set<String> emails = p.getEmail();
            Address a = p.getAddress();

        %>
        <section class="container">
            <%@include file="includes/header.jsp"%>
            <div class="track-list">
                <%=p.getFirstname()%>
                <%=p.getLastname()%>
                <%=p.getUsername()%>
                <%=p.getAge()%>
                <%
                    Iterator it = emails.iterator();
                    while (it.hasNext()) {
                %><%=it.next()%><%
                    }
                %><%=a.getCity()%>

            </div>
        </section>
    </body>
</html>
