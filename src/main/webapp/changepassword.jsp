<%-- 
    Document   : changepassword
    Created on : Nov 15, 2015, 9:18:47 PM
    Author     : Salano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% 
                String msg = (String) session.getAttribute("msg");
                if(msg != null){
            %>
                    <p id="msg"><%=msg%></p>
            <% }
            %>
        <form method="get"  action="Register">
                <table>
                    <tr>
                        <td><label for="username">Username </label><input type="text" name="username" /></td>
                    </tr>
                    <tr>
                        <td><label for="password">Password </label><input type="password" name="password" /></td>
                    </tr>
                    <tr>
                        <td><label for="cpassword">Confirm Password </label><input type="password" name="cpassword" /></td>
                    </tr>
                    <tr><td><input type="submit" value="Register"></td></tr>
                </table>
        </form>
    </body>
</html>
