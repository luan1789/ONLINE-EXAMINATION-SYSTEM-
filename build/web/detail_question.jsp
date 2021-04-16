<%-- 
    Document   : detail_question
    Created on : Feb 8, 2021, 1:51:36 PM
    Author     : PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detail Question </title>
        <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
        <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
        <style><%@include file="css/admin.css" %> </style>
    </head>
    <body>

        <jsp:include page="ad_header.jsp"></jsp:include>  	
            <div class="container-fluid main-container">
                <div class="col-md-2 sidebar">
                    <div class="row">
                        <div class="absolute-wrapper">
                        <jsp:include page="ad_left.jsp"></jsp:include>
                        </div>
                    </div>  		
                </div>
                <div class="col-md-10 content">

                    <div class="container-fluid px-1 py-5 mx-auto">
                        <div class="row d-flex justify-content-center">

                            <div class="col-xl-7 col-lg-8 col-md-9 col-11 text-center">
                                <h3>Detail question ${requestScope.QUESTION.questionID}</h3>
                            <div class="card">
                                <form class="form-card" onsubmit="return validate(0)" action="update">
                                    <input type="hidden" value="${requestScope.QUESTION.questionID}" name="txtQuestionID">
                                    <div class="row justify-content-between text-left">
                                        <div class="form-group col-12 flex-column d-flex"> <label class="form-control-label px-3">Content<span class="text-danger"> *</span></label> <textarea type="text" id="ans" name="txtQuestion_content" cols="50">${requestScope.QUESTION.question_content}</textarea> </div>
                                    </div>
                                    <c:forEach var="as" items="${requestScope.QUESTION.answer_content}" varStatus="counter">
                                        <input type="hidden" name="txtAnswerID${counter.count}" value="${as.answerID}">
                                        <div class="row justify-content-between text-left">
                                            <div class="form-group col-sm-6 flex-column d-flex"> 
                                                <label class="form-control-label px-3">Answer_content${counter.count}<span class="text-danger"> *</span></label> 
                                                <input type="text"  id="fname" name="txtAnswer_content${counter.count}" value="${as.content}" > </div>
                                        </div>
                                      
                                    </c:forEach>

                                    <div class="row justify-content-between text-left">
                                        <div class="form-group col-sm-6 flex-column d-flex"> <label class="form-control-label px-3">Answer_correct<span class="text-danger"> *</span></label>  
                                            <select name="txtAnswer_correct">
                                                <c:forEach var="as" items="${requestScope.QUESTION.answer_content}" varStatus="counter">
                                                    <option value="txtAnswer_content${counter.count}" <c:if test="${as.isCorrect==true}">selected</c:if>>
                                                        Answer_content${counter.count}
                                                    </option>
                                                </c:forEach>

                                            </select>
                                        </div>
                                    </div>


                                    <div class="row justify-content-between text-left">
                                        <div class="form-group col-sm-6 flex-column d-flex"> 
                                            <label class="form-control-label px-3">Subject<span class="text-danger"> *</span></label>
                                            <select name="Sub">
                                                <c:if test="${sessionScope.LIST_SUB!=null}">
                                                    <c:set var="sub" value="${requestScope.QUESTION.subject}"></c:set>
                                                    <c:forEach var="type" items="${sessionScope.LIST_SUB}">
                                                        <option value="${type.subjectID}" <c:if test="${type.subjectID == sub}">selected</c:if>>
                                                            ${type.subjectID}-${type.subjectName}
                                                        </option>
                                                    </c:forEach>
                                                </c:if>

                                            </select>
                                        </div>
                                    </div>
                                    <div class="row justify-content-between text-left">
                                        <div class="form-group col-sm-6 flex-column d-flex"> 
                                            <label class="form-control-label px-3">Status<span class="text-danger"> *</span></label>
                                            <select name="Status">
                                                <c:set var="status" value="${requestScope.QUESTION.status}"></c:set>
                                                <option value="Active" <c:if test="${status == true}">selected</c:if>>
                                                        Active
                                                    </option>
                                                    <option value="InActive"<c:if test="${status == false}">selected</c:if>>
                                                    InActive
                                                </option>
                                            </select>
                                        </div>
                                    </div>

                                    <div class="row justify-content-end">
                                        <div class="form-group col-sm-6"> <button type="submit" class="btn-block btn-primary">Update</button> </div>
                                    </div>

                                </form>
                            </div>
                        </div>
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
