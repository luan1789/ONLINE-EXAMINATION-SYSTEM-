<%-- 
    Document   : login
    Created on : Feb 8, 2021, 11:02:22 AM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <style><%@include file="/css/login.css"%></style>
        <script type="text/javascript"><%@include file="/js/login.js"%></script>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="row">
            <div class="col-md-6 mx-auto p-0">
                <div class="card">
                    <div class="login-box">
                        <div class="login-snip"> <input id="tab-1" type="radio" name="tab" class="sign-in" checked><label for="tab-1" class="tab">Login</label> <input id="tab-2" type="radio" name="tab" class="sign-up"><label for="tab-2" class="tab">Sign Up</label>
                            <div class="login-space">
                                <form action="login" method="POST">
                                    <div class="login">
                                        <div class="group"> <label for="user" class="label">Email</label>
                                            <input id="user" type="email" class="input" placeholder="Enter your email" name="txtEmail" required> </div>
                                        <div class="group"> <label for="pass" class="label">Password</label>
                                            <input id="pass" type="password" class="input" data-type="password" placeholder="Enter your password" name="txtPassword" required> </div>
                                        <div class="group"> <input id="check" type="checkbox" class="check" checked> <label for="check"><span class="icon"></span> Keep me Signed in</label> </div>
                                        <font color="white">${requestScope.ERROR_LOGIN}</font>
                                        <div class="group"> <input type="submit" class="button" value="login"> </div>
                                        <div class="hr"></div>
                                        <div class="foot"> <a href="#">Forgot Password?</a> </div>
                                    </div>
                                </form>
                                <form action="createUser" method="POST">
                                    <div class="sign-up-form">
                                        <div class="group"> <label for="user" class="label">Username</label>
                                            <input id="user" type="text" name="txtUsername" required class="input" placeholder="Create your Username"> </div>
                                        <div class="group"> <label for="pass" class="label">Password</label> 
                                            <input id="pass" type="password" name="txtPassword" required class="input" data-type="password" placeholder="Create your password"> </div>
                                        <div class="group"> <label for="pass" class="label">Repeat Password</label> 
                                            <input id="pass" type="password" name="txtRePassword" required class="input" data-type="password" placeholder="Repeat your password"> </div>
                                        <div class="group"> <label for="pass" class="label">Email Address</label> 
                                            <input id="email" type="email" name="txtEmail" required   class="input" placeholder="Enter your email address"> </div>
                                        <font color="white">${requestScope.ERROR_CREATE}</font>
                                        <div class="group"> <input type="submit"  class="button" value="Sign Up"> </div>
                                        <div class="hr"></div>
                                        <div class="foot"> <label for="tab-1">Already Member?</label> </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>
