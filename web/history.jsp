<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tagliburi = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Quiz Online</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="http://netdna.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">
        <style><%@include file="css/home.css" %> </style>

        <link href='https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css'>
        <script src='https://stackpath.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.bundle.min.js'></script>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>
    </head>
    <body>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/material-design-iconic-font/2.2.0/css/material-design-iconic-font.min.css" integrity="sha256-3sPp8BkKUE7QyPSl6VfBByBroQbKxKG7tsusY2mhbVY=" crossorigin="anonymous" />
        <header class="header-area overlay">
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


            <div class="banner">
                <div class="container" id="exam">
                    <h4 class="d-flex justify-content-between align-items-center mb-3">
                        <c:set var="list" value="${sessionScope.HISTORY}"></c:set>
                        <c:if test="${list!=null }">
                            <form action="searchHistory">

                                <jsp:useBean id="maxDate"  class="java.util.Date"></jsp:useBean>
                                <input type="date"   max="<fmt:formatDate pattern = "yyyy-MM-dd"  value="${maxDate}"/>" name="txtDate" value="${param.txtDate}">
                                <input type="text" name="txtSub"value="${param.txtSub}">
                                <button  type="submit" >Search</button>
                            </form>
                        </h4>
                        <!-- Cart -->
                        <ul class="list-group mb-3 z-depth-1">
                            <table border="0">
                                <thead>
                                    <tr> 
                                        <th>Exam ID</th>
                                        <th>Subject</th>
                                        <th>Time Start</th>
                                        <th>Time End</th>
                                        <th>Point</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="f" items="${list}" varStatus="counter">
                                    <form action="examDetail">
                                        <tr>
                                            <td>
                                                ${f.examID}
                                            </td>
                                            <td>
                                                ${f.subjectID}
                                            </td>
                                            <td> <fmt:formatDate type = "both" 
                                                            dateStyle = "short" timeStyle = "short"  value="${f.timeStart}"/></td>
                                            <td> <fmt:formatDate type = "both" 
                                                            dateStyle = "short" timeStyle = "short"  value="${f.timeEnd}"/></td>
                                            <td>
                                                ${f.point}
                                            </td>   
                                            <td> 
                                                <input type="hidden" name="txtExamID" value="${f.examID}" >
                                                <input type="submit"  value="Detail">
                                            </td>
                                        </tr>
                                    </form>   
                                </c:forEach>
                                </tbody>
                            </table>  
                        </ul>
                        <table border="1" style="margin: auto">
                            <c:url var="nextpage" value="searchHistory">
                                <c:param name="txtSub" value="${param.txtSub}"></c:param>
                                <c:param name="txtSearch" value="${param.txtSearch}"></c:param>
                                <c:param name="txtDate" value="${param.txtDate}"></c:param>
                            </c:url>
                            <tr>
                                <c:if test="${requestScope.currentPage != 1}">
                                    <td> <a  style="float: left" href="${nextpage}&page=${requestScope.currentPage - 1}">Previous</a></td>
                                </c:if>
                                <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                                    <c:choose >
                                        <c:when test="${requestScope.currentPage == i}">
                                            <td style="width: 20px;color: red">${i}</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="width: 20px"><a href="${nextpage}&page=${i}">${i}</a></td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                    <c:if test="${requestScope.currentPage < requestScope.noOfPages}">
                                    <td ><a style="float: right" href="${nextpage}&page=${requestScope.currentPage + 1}">Next</a></td>
                                </c:if>
                            </tr>
                        </table>

                    </div>

                </c:if>
                <c:if test="${(list==null) or (list.size()== 0)}">
                    <div class="col-sm-12  col-md-6" style="text-align: center;margin: auto" >
                        <h1>NO HISTORY</h1>
                        <a href="home"
                           class="btn btn-block btn-light">Home</a>
                    </div>
                </c:if>
            </div>

        </header>

        <script src="http://code.jquery.com/jquery-1.10.2.min.js"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            function next() {
                document.getElementById()("currentQues").value = ${requestScope.currentQues+1 };
            }
            function previous() {
                document.getElementById("currentQues").value = ${requestScope.currentQues-1 };
            }
            jQuery(function ($) {
                $(window).on('scroll', function () {
                    if ($(this).scrollTop() >= 200) {
                        $('.navbar').addClass('fixed-top');
                    } else if ($(this).scrollTop() == 0) {
                        $('.navbar').removeClass('fixed-top');
                    }
                });

                function adjustNav() {
                    var winWidth = $(window).width(),
                            dropdown = $('.dropdown'),
                            dropdownMenu = $('.dropdown-menu');

                    if (winWidth >= 768) {
                        dropdown.on('mouseenter', function () {
                            $(this).addClass('show')
                                    .children(dropdownMenu).addClass('show');
                        });

                        dropdown.on('mouseleave', function () {
                            $(this).removeClass('show')
                                    .children(dropdownMenu).removeClass('show');
                        });
                    } else {
                        dropdown.off('mouseenter mouseleave');
                    }
                }

                $(window).on('resize', adjustNav);

                adjustNav();
            });
        </script>
    </body>
</html>
