<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <%@include file="includes/validateLogin.jsp"%>
    <head>
        <%@include file="includes/head.jsp"%>
        <title>JSP Page</title>
    </head>
    <body>
        <section class="container">
            <%@include file="includes/header.jsp"%>

            <form class="form-login" method = "POST" action="Music" enctype="multipart/form-data">
                <h2 class="form-login-heading">Upload Your Musical Masterpiece</h2>
                <div class="login-wrap">
                    <input type="file" name="upfile">
                    <br />
                    <button class="btn btn-theme btn-block" type="submit"><i class="fa fa-lock"></i>UPLOAD</button>
                </div>          
            </form>	  
        </section>
    </body>
</html>
