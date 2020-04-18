<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <jsp:include page="layout/head.jsp"/>
    <style type="text/css">
        .tableheadercell {
            padding: 16px;
            font-size: 16px;
            font-weight: bold;
            border: none;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="wrapper">
    <jsp:include page="layout/navbar.jsp"/>
    <div class="container">
        <div class="tableheadercell">
            <h1>Reset link will be sent to your email</h1/
        </div>
    </div>
    <div class="container">
        <form:form modelAttribute="forgetPassword" action="/forgetPassword" method="post">
            <div class="form-row justify-content-md-center">
                <div class="col-4">
                    <input id="email" name="email" type="email" placeholder="Enter your email" class="form-control"/>
                </div>

                <div class="col-2 justify-content-md-center">
                    <button type="submit" id="resetPassword" class="btn btn-primary"><spring:message code="reset.password"/></button>
                </div>
            </div>
            <hr/>
        </form:form>
    </div>
</div><!--theme-layout end-->
<jsp:include page="layout/footerScript.jsp"/>
</body>
<script type="text/javascript" src="/js/postAndUserManagement_Aser.js"></script>
</html>