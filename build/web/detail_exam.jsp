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

        <script src="https://use.fontawesome.com/releases/v5.15.2/js/all.js" data-auto-replace-svg="nest"></script>
        <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
        <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
    </head>
    <body>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css" integrity="sha256-3sPp8BkKUE7QyPSl6VfBByBroQbKxKG7tsusY2mhbVY=" crossorigin="anonymous" />
        <header class="header-area ">
            <nav class="navbar navbar-expand-md navbar-dark">
                <div class="container">
                    <a href="home"style="color: white" class="navbar-brand">Quiz Online</a>

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

            <div class="container" id="exam">

                <div class="container mt-sm-5 my-1">
                    <c:set var="exam" value="${sessionScope.EXAM_DETAIL}"></c:set>
                    <c:if test="${ not empty exam}">
                        <c:forEach var="e" items="${exam.list}" varStatus="counter">
                            <div ><b>Q.${counter.count} </b></div><br>
                            <div ><b>${e.question_content}</b></div><br>
                            <div class="ml-md-3 ml-sm-3 pl-md-5 pt-sm-0 pt-3" id="options"> 
                                <c:set var="answer" value="${e.answerIDUser}"></c:set>
                                <c:forEach var="as" items="${e.answer_content}" >
                                    <label class="options">${as.content} 
                                        <input value="${as.answerID}"  disabled type="radio" 
                                               <c:if test="${answer == as.answerID}">checked
                                               </c:if>>
                                        <c:choose>
                                            <c:when test="${as.isCorrect}">
                                                <i class="fas fa-check-circle"style="color:greenyellow"></i>
                                            </c:when>
                                            <c:otherwise> 
                                                <i class="fas fa-times-circle" style="color:red"></i>
                                            </c:otherwise>
                                        </c:choose>

                                        <span class="checkmark"></span> </label> 
                                    </c:forEach>
                            </div>
                        </c:forEach>

                    </div>
                </div>
            </c:if>
        </header>


        <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script type="text/javascript">

        </script>
    </body>
</html>
