

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@tagliburi = "http://java.sun.com/jsp/jstl/fmt"  prefix = "fmt" %>
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
            <c:redirect url="LogoutController"></c:redirect>
        </c:if>
        <jsp:include page="ad_header.jsp"></jsp:include>
            <div class="container-fluid main-container">
                <div class="col-md-2 sidebar">
                    <div class="row">
                        <!-- uncomment code for absolute positioning tweek see top comment in css -->
                        <div class="absolute-wrapper"> </div>
                        <!-- Menu -->
                    <jsp:include page="ad_left.jsp"></jsp:include>
                    </div>  		
                </div>
                <div class="col-md-10 content">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <nav class="navbar navbar-expand-lg navbar-dark mdb-color lighten-3 mt-3 mb-5">

                                <!-- Navbar brand -->
                                <div class="collapse navbar-collapse" id="basicExampleNav">

                                    <!-- Links -->

                                    All Questions  <a href="create_question_page">New Question</a>
                                    <form action="search">
                                        Category:
                                        <select name="txtSub">
                                        <c:set var="typeSub" value="${param.txtSub}"></c:set>
                                            <option>All</option>
                                        <c:forEach var="type" items="${sessionScope.LIST_SUB}">
                                            <option value="${type.subjectID}" <c:if test="${type.subjectID == typeSub}">selected</c:if>>
                                                ${type.subjectID}-${type.subjectName}
                                            </option>
                                        </c:forEach>
                                    </select>
                                    Status:
                                    <select name="txtStatus">
                                        <c:set var="status" value="${param.txtStatus}"></c:set>
                                        <option value="All"<c:if test="${'All' == status}">selected</c:if>>All</option>
                                        <option value="Active"<c:if test="${'Active' == status}">selected</c:if>>Active</option>
                                        <option value="InActive"<c:if test="${'InActive' == status}">selected</c:if>>InActive</option>
                                        </select>
                                        <input type="text" name="txtSearch" placeholder="Search"value="${param.txtSearch}"/>
                                    <input type="submit"  value="SearchAD"/>

                                </form>
                            </div>
                            <!-- Collapsible content -->
                        </nav>
                    </div>
                    <div class="panel-body">
                        <c:set var="list" value="${sessionScope.LIST_QUESTIONS}"></c:set>
                        <c:if test="${not empty list}">

                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th scope="col">ID</th>
                                        <th scope="col">QUESTION</th>
                                        <th scope="col">ANSWER</th>
                                        <th scope="col">SUBJECT</th>
                                        <th scope="col" class="text-right">STATUS</th>
                                        <th scope="col" class="text-right">EDIT</th>
                                        <th scope="col" >DELETE</th>

                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="e" items="${list}" varStatus="counter">
                                    <form action="editQuestion">
                                        <tr> 
                                            <td> ${e.questionID}</td>
                                            <td> ${e.question_content}</td>
                                            <td>
                                                <c:forEach var="as" items="${e.answer_content}">
                                                    <c:if test="${as.isCorrect==true}">
                                                        ${as.content}<br>
                                                    </c:if>
                                                </c:forEach>
                                            </td>
                                                <!--<td> <fmt:formatDate pattern = "dd-MM-yyyy"  value="${e.createDate}"/></td>-->
                                            <td>${e.subject}</td>
                                            <td class="text-right">
                                                <c:choose >
                                                    <c:when test="${e.status == true}">
                                                        <p style="color: blue">Active</p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p style="color: red">InActive</p>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td class="text-right"> 
                                                <input type="hidden" name="txtQuestionID" value="${e.questionID}">
                                                <button type="submit" class="btn btn-success">Edit</button>
                                            </td>
                                    </form>

                                    <td >    
                                        <form action="delete">
                                            <c:if test="${e.status == true}">
                                                <input type="hidden" name="txtQuestionID" value="${e.questionID}">
                                                <button class="btn  btn-danger"type="submit" onclick="return confirm('Do you want to delete question?')" >Delete </button>
                                            </c:if> 
                                        </form>

                                    </td>
                                    </tr>
                                </c:forEach>


                                </tbody>
                            </table> 



                            <table border="1" style="margin: auto">
                                <c:url var="nextpage" value="search">
                                    <c:param name="txtSub" value="${param.txtSub}"></c:param>
                                    <c:param name="txtSearch" value="${param.txtSearch}"></c:param>
                                    <c:param name="txtStatus" value="${param.txtStatus}"></c:param>
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
                        </c:if>
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
