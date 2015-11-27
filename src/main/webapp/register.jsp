<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Karaoke - Register</title>
        <%@include file="includes/head.jsp"%>
    </head>

    <body>
        <section class="container">
            <%@include file="includes/header.jsp"%>
            <div id="login-page">
                <div class="container">
                    <form class="form-login" method="POST"  action="Register" style="max-width: 600px">
                        <h2 class="form-login-heading">Register</h2>
                        <%
                            String msg = (String) session.getAttribute("msg");
                            if (msg != null) {
                        %>
                        <p id="msg"><%=msg%></p>
                        <% }
                        %>

                        <div class="login-wrap">
                            <div class="row">
                                <div class="col-md-6">
                                    <input type="text" name="firstname" class="form-control" placeholder="First Name">
                                    <br />
                                    <input type="text" name="lastname" class="form-control" placeholder="Last Name">
                                    <br />
                                    <input type="text" name="username" class="form-control" placeholder="Username">
                                    <br />
                                    <input type="password" name="password" class="form-control" placeholder="Password">
                                    <br />
                                    <input type="password" name="cpassword" class="form-control" placeholder="Confirm Password">
                                    <br />
                                </div>
                                <div class="col-md-6">
                                    <input type="text" name="email" class="form-control" placeholder="Email">
                                    <br />
                                    <input type="text" name="age" class="form-control" placeholder="Age">
                                    <br />
                                    <input type="text" name="street" class="form-control" placeholder="Street">
                                    <br />
                                    <input type="text" name="city" class="form-control" placeholder="City">
                                    <br />
                                    <input type="text" name="zip" class="form-control" placeholder="Postcode">
                                    <br />
                                </div>
                            </div>
                            <button class="btn btn-theme btn-block" type="submit"><i class="fa fa-lock"></i> REGISTER</button>
                            <hr>
                        </div>
                    </form>
                </div>
            </div>
        </section>
        <script src="assets/js/jquery.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <!--BACKSTRETCH-->
        <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
        <script type="text/javascript" src="assets/js/jquery.backstretch.min.js"></script>
        <script>
            $.backstretch("assets/img/login-bg.jpg", {speed: 500});
        </script>
    </body>
</html>
