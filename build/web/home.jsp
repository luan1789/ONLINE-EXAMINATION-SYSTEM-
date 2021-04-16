<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Quiz Online</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="http://netdna.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style><%@include file="css/home.css" %> </style>
    </head>
    <body>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css" integrity="sha256-3sPp8BkKUE7QyPSl6VfBByBroQbKxKG7tsusY2mhbVY=" crossorigin="anonymous" />
        <header class="header-area overlay">
            <nav class="navbar navbar-expand-md navbar-dark">
                <div class="container">
                    <a href="home" style="color: white" class="navbar-brand">Quiz Online</a>

                    <button type="button" class="navbar-toggler collapsed" data-toggle="collapse" data-target="#main-nav">
                        <span class="menu-icon-bar"></span>
                        <span class="menu-icon-bar"></span>
                        <span class="menu-icon-bar"></span>
                    </button>

                    <div id="main-nav" class="collapse navbar-collapse">
                        <ul class="navbar-nav ml-auto">
                            <li><a style="color: white" class="nav-item nav-link active">Welcome ${sessionScope.LOGIN_USER.name}</a></li>					
                            <li><a href="searchHistory" class="nav-item nav-link">History</a></li>
                            <li><a href="logout" class="nav-item nav-link">Logout</a></li>
                        </ul>
                    </div>
                </div>
            </nav>

            <div class="banner">
                <div class="container">
                    <h1 >Welcome to Quiz Online</h1>
                    <p>Please select the subject to continue</p>
                    <form action="startExam" method="post">
                        <div class="select-container">
                            <select id="subject" class="custom-select" name="txtSub">
                                <option  disabled selected>Select Subject</option>
                                <c:set var="list" value="${sessionScope.LIST_SUB}"></c:set>
                                <c:if test="${not empty list}">
                                    <c:forEach var="e" items="${list}" varStatus="counter">
                                        <option value="${e.subjectID}">${e.subjectID}-${e.subjectName}</option>
                                    </c:forEach>
                                </c:if>
                            </select>
                        </div>

                        <button  id="submit" type="submit" class="button button-primary">Get Started</button>
                    </form>
                </div>
            </div>
        </header>


       <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
        <script >
            $(document).ready(function () {
                $("#submit").click(function () {

                    var books = $('#subject');
                    if (books.val() == null ) {
                        alert("Please select subject from the list and then proceed!");
                        $('#subject').focus();

                        return false;
                    } else
                        return;
                });
            });
        </script>
    </body>
</html>
