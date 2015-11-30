<!DOCTYPE html>
<html lang="en">
    <head>
      <%@include file="includes/head.jsp"%>
        <title>Karaoke - Login</title>
    </head>

    <body>
        <section class="container">
           <!-- <%@include file="includes/header.jsp"%>   -->
            <div id="login-page">
                <div class="container">
                    <form class="form-login" action="Login" method="POST">

                        <h2 class="form-login-heading">sign in now</h2>
                        <div class="login-wrap">
                            <input type="text" name="username" class="form-control" placeholder="Username" autofocus>
                            <br />
                            <input type="password" name="password" class="form-control" placeholder="Password">
                            <br />
                            <button class="btn btn-theme btn-block" type="submit"><i class="fa fa-lock"></i> SIGN IN</button>
                            <hr>
                            <div class="registration">
                                Don't have an account yet?<br/>
                                <a class="" href="register.jsp">
                                    Create an account
                                </a>
                            </div>
                        </div>

                        <div aria-hidden="true" aria-labelledby="myModalLabel" role="dialog" tabindex="-1" id="myModal" class="modal fade">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title">Forgot Password ?</h4>
                                    </div>
                                    <div class="modal-body">
                                        <p>Enter your e-mail address below to reset your password.</p>
                                        <input type="text" name="email" placeholder="Email" autocomplete="off" class="form-control placeholder-no-fix">
                                    </div>
                                    <div class="modal-footer">
                                        <button data-dismiss="modal" class="btn btn-default" type="button">Cancel</button>
                                        <button class="btn btn-theme" type="button">Submit</button>
                                    </div>
                                </div>
                            </div>
                        </div>                                     
                    </form>	  	
                </div>
            </div>
        </section>
        <!-- load scripts last so page loads faster-->
        <script src="assets/js/jquery.js"></script>
        <script src="assets/js/bootstrap.min.js"></script>
        <!--BACKSTRETCH-->
        <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
        <script type="text/javascript" src="assets/js/jquery.backstretch.min.js"></script>
        <script>
            $.backstretch("assets/img/bg1.jpg", {speed: 500});
        </script>
    </body>
</html>
