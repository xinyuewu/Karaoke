<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Register</title>

        <link href="assets/css/bootstrap.css" rel="stylesheet">
        <link href="assets/font-awesome/css/font-awesome.css" rel="stylesheet" />
        <link href="assets/css/style.css" rel="stylesheet">
        <link href="assets/css/style-responsive.css" rel="stylesheet">  
    </head>

    <body>
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
                                <input type="text" class="form-control" placeholder="First Name">
                                <br>
                                <input type="text" class="form-control" placeholder="Last Name">
                                <br>
                                <input type="text" class="form-control" placeholder="Username">
                                <br>
                                <input type="password" class="form-control" placeholder="Password">
                                <br>
                                <input type="password" class="form-control" placeholder="Confirm Password">
                                <br>
                            </div>
                            <div class="col-md-6">
                                <input type="text" class="form-control" placeholder="Email">
                                <br>
                                <input type="text" class="form-control" placeholder="Age">
                                <br>
                                <input type="text" class="form-control" placeholder="Street">
                                <br>
                                <input type="text" class="form-control" placeholder="City">
                                <br>
                                <input type="text" class="form-control" placeholder="Postcode">
                                <br>
                            </div>
                        </div>

                        <button class="btn btn-theme btn-block" type="submit"><i class="fa fa-lock"></i> REGISTER</button>
                        <hr>

                        <div class="login-social-link centered">
                            <p>or you can sign in via your social network</p>
                            <button class="btn btn-facebook" type="submit"><i class="fa fa-facebook"></i> Facebook</button>
                            <button class="btn btn-twitter" type="submit"><i class="fa fa-twitter"></i> Twitter</button>
                        </div>

                    </div>
                </form>
            </div>
        </div>



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
