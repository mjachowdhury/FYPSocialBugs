<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="layout/head.jsp"/>
</head>
<body>
<jsp:include page="layout/navbar.jsp"/>

   <h1 align="center"><c:out value="${title}"/></h1>
   <hr/>
   <p align="center"><c:out value="${message}"/> </p>
   <hr/>
    <p  align="center"><a href="/register/resendRegistrationToken?token=${token}">Resend Token</a></p>

</body>
</html>