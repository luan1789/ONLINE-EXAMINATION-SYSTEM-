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
                    <form role="form"  method="post">                      
                        <div class="container mt-sm-5 my-1">
                            <div class="question ml-sm-5 pl-sm-5 pt-2">
                                <table class="table table-striped">
                                    <thead>
                                        <tr>
                                            <th scope="col">SUBJECT</th>
                                            <th scope="col">TOTAL</th>
                                            <th scope="col">CORRECT</th>
                                            <th scope="col">SCORE</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:set var="exam" value="${sessionScope.EXAM}"></c:set>
                                            <tr> 
                                                <td> ${exam.subjectID}</td>
                                            <td> ${exam.numberQuestion}</td>
                                            <td> ${exam.numberCorrect}</td>
                                            <td> ${exam.point}</td>
                                        </tr>
                                    </tbody>
                                </table> 

                            </div>


                    </form>
                </div>
                <a href="detailExam" class="button button-primary">Detail</a>
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
