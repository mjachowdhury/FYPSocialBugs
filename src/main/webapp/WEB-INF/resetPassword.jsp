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
            <h1>Reset Password<h1/>
        </div>
    </div>
    <div class="container">
        <div class="row justify-content-lg-center">
            <div class="col-lg-4 col-sm-12 pb-4">
                <div class="card">
                    <div class="card-body">
                        <form:form action="/forgetPassword/reset" method="POST">
                            <div class="form-row justify-content-md-center">
                                <input id="id" name="id" type="hidden" value="${id}"/>
                                <div class="col-12">
                                    <input id="password" name="password" type="password" placeholder="Enter your password" class="form-control"/>
                                </div>

                                <div class="col-12 pb-2 pt-5">
                                    <button type="submit" id="getReportBtn" class="btn btn-primary float-right"><spring:message code="reset.password"/></button>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div><!--theme-layout end-->
<jsp:include page="layout/footerScript.jsp"/>
</body>
<script type="text/javascript" src="/js/postAndUserManagement_Aser.js"></script>
</html>