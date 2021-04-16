<%-- 
    Document   : admin
    Created on : Oct 18, 2020, 4:26:41 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Page</title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <style><%@include file="css/admin.css" %> </style>
    </head>
    <body>
        <c:if test="${sessionScope.LOGIN_USER.roleID !='AD'}">
            <c:redirect url="logout"></c:redirect>
        </c:if>
        <jsp:include page="ad_header.jsp"></jsp:include>
            <div class="container-fluid main-container">
                <div class="col-md-2 sidebar">
                    <div class="row">
                        <!-- uncomment code for absolute positioning tweek see top comment in css -->
                        <div class="absolute-wrapper"> </div>
                    <jsp:include page="ad_left.jsp"></jsp:include>
                    <!-- Menu -->
                </div>  		
            </div>
            <div class="col-md-10 content">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        Dashboard
                    </div>
                    <div class="panel-body">
                        <section class="jumbotron text-center"  style="background-image: url('http://lebow.drexel.edu/sites/default/files/story/1502995524-questions.jpg'); 
                                 background-repeat: no-repeat; background-size: cover; color:#00f;">
                            <div class="container">
                                <h1 class="jumbotron-heading">THE QUESTION BANK</h1>
                            </div>
                        </section>                     

                    </div>
                </div>
            </div>
            <footer class="pull-left footer">
                <p class="col-md-12">
                <hr class="divider">
                Copyright &COPY; 2015 <a href="#">Gravitano</a>
                </p>
            </footer>
        </div>
    </body>
</html>
