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
        <title>Profile</title>
        
    </head>
    <body>
        <%  Person p = (Person) request.getAttribute("person");
            Set<String> emails = p.getEmail();
            Address a = p.getAddress();
        %>
        <section class="container">
            <%@include file="includes/header.jsp"%>
           
            <form class="form-login" method="POST" action="User">
                <h2 class="form-login-heading">Your Information</h2>
                <div class="login-wrap">
                    <div class="row">
                        <div class="col-md-6">
                            <input type="text" value="<%=p.getFirstname()%>" name= "firstname" class="form-control" placeholder="First Name">
                            <br />
                            <input type="text" value="<%=p.getLastname()%>"  name="lastname" class="form-control" placeholder="Last Name">
                            <br />
                            <input type="text" value="<%=p.getUsername()%>" name="username" class="form-control" placeholder="Username" readonly>
                            <br />
                            <input type="text" value="<%=emails.toArray()[0]%>" name="email" class="form-control" placeholder="Email">
                            <br />
                        </div>
                        <div class="col-md-6">
                            <input type="text" value="<%=p.getAge()%>" name="age" class="form-control" placeholder="Age" readonly>
                            <br />
                            <input type="text" value="<%=a.getStreet()%>" name="street" class="form-control" placeholder="Street">
                            <br />
                            <input type="text" value="<%=a.getCity()%>" name="city" class="form-control" placeholder="City">
                            <br />
                            <input type="text" value="<%=a.getZip()%>" name="zip" class="form-control" placeholder="Postcode">
                            <br />
                        </div>
                    </div>
                    <button class="btn btn-theme btn-block" type="submit"><i class="fa fa-lock"></i>UPDATE</button>
                    <hr>
                </div>
            </form>
        </section>
        <script src="/Karaoke/assets/js/jquery.js"></script>
        <script src="/Karaoke/assets/js/trackFunctions.js"></script>
        <script src="/Karaoke/assets/js/jquery.js"></script>
        <script src="/Karaoke/assets/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/Karaoke/assets/js/jquery.backstretch.min.js"></script>
        <script>
               $.backstretch("/Karaoke/assets/img/karaheader.png", {speed: 500});
        </script>
    </body>
    
</html>
