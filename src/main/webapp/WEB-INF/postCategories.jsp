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
            <h1><spring:message code="PostCategory.header"/>
        </div>
    </div>
    <div class="container">
        <form:form modelAttribute="postCategory" action="/" method="post">
            <div class="form-row justify-content-md-center">
                <input id="categoryId" type="hidden" value="0"/>
                <div class="col-4">
                    <input id="categoryTitle" type="text" placeholder="Category title" class="form-control"/>
                </div>
                <div class="col-4">
                    <input id="categoryDesc" type="text" placeholder="Description" class="form-control"/>
                </div>
                <div class="col-2">
                    <input id="categoryOrder" type="number" value="0" min="0" placeholder="List Order" class="form-control"/>
                </div>
                <div class="col-2 justify-content-md-center">
                    <button type="button" id="addPostCategory" class="btn btn-primary"><spring:message code="PostCategory.add"/></button>
                </div>
            </div>
            <hr/>

            <table class="table pt-5">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Title</th>
                        <th scope="col">Description</th>
                        <th scope="col">order</th>
                        <th scope="col"></th>
                    </tr>
                </thead>
                <tbody id="postCategoryList">
                    <c:forEach var="i" items="${postCategories}" varStatus="loop">
                    <tr id="postCategory-${i.id}">
                        <th scope="row"><c:out value="${loop.index + 1}"/></th>
                        <td>
                            <c:out value="${i.title}"/>
                        </td>
                        <td>
                            <c:out value="${i.description}"/>
                        </td>
                        <td>
                            <c:out value="${i.listOrder}"/>
                        </td>
                        <td>
                            <a href="javascript:;" class="updatePostCategory" data-id="${i.id}" data-title="${i.title}" data-desc="${i.description}" data-order="${i.listOrder}"><spring:message code="PostCategory.update"/></a>
                        </td>
                        <td>
                            <a href="javascript:;" class="deletePostCategory" data-id="${i.id}" data-title="${i.title}"><spring:message code="PostCategory.delete"/></a>
                        </td>
                    </tr>
                    </c:forEach>
                </tbody>
            </table>
        </form:form>
    </div>
</div><!--theme-layout end-->
<jsp:include page="layout/footerScript.jsp"/>
</body>
<script type="text/javascript" src="/js/postAndUserManagement_Aser.js"></script>
</html>