<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="utils" class="edu.mum.ea.socialnetwork.util.Utils"/>

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
        <div class="tableheadercell pt-3">
            <h1><spring:message code="report.header"/>
        </div>

        <hr/>

        <div class="row">
            <div class="col-lg-4 col-sm-12 pb-4">
                <div class="card">
                    <div class="card-body">

                        <form:form action="/report/" method="GET">
                            <div class="form-row justify-content-md-center">
                                <div class="col-12 pb-2">
                                    <select class="browser-default custom-select" name="pcid">
                                        <option value="0">Open this select menu</option>
                                        <c:forEach var="category" items="${postCategories}" varStatus="loop">
                                        <option value="${category.id}" ${category.id == pcid ? 'selected' : ''}><c:out value="${category.title}"/></option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-12 pb-2">
                                    <select class="browser-default custom-select" name="d">
                                        <option value="7" ${d == 7 ? 'selected' : ''}>Weekly</option>
                                        <option value="30" ${d == 30 ? 'selected' : ''}>Monthly</option>
                                        <option value="365" ${d == 365 ? 'selected' : ''}>Yearly</option>
                                    </select>
                                </div>
                                <div class="col-12 pb-2">
                                    <button type="submit" id="getReportBtn" class="btn btn-primary float-right"><spring:message code="report.submit"/></button>
                                </div>
                            </div>
                        </form:form>

                        <hr/>

                        <div class="row">
                            <div class="col-8">
                                <p class="text-right">Number of filtered posts: </p>
                            </div>
                            <div class="col-4">
                                <p class="text-center"><c:out value="${numberOfPosts}"/></p>
                            </div>
                        </div>
                        <hr/>
                        <div class="row">
                            <div class="col-8">
                                <p class="text-right">Total number of posts: </p>
                            </div>
                            <div class="col-4">
                                <p class="text-center"><c:out value="${totalNumberOfPosts}"/></p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-lg-8 col-sm-12">
                <div class="row">
                    <div class="col">
                        <div class="card">
                            <div class="card-body">

                                <div class="posts-section">
                                        <c:forEach var="post" items="${posts}">
                                            <div class="post-bar">
                                                <div class="post_topbar">
                                                    <div class="usy-dt">
                                                        <c:choose>
                                                            <c:when test="${post.user.profile.profilePhoto.length()>4}">
                                                                <img src="/media/profile/${post.user.profile.profilePhoto}"
                                                                     alt="" width="45px" height="45px">
                                                            </c:when>
                                                            <c:otherwise>
                                                                <img src="<c:url value='/images/user.jpg'/>" alt=""
                                                                     width="45px" height="45px">
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <div class="usy-name">
                                                            <a href="<c:url value='/profile/${post.user.id}' />">
                                                                <h3>${post.user.profile.firstName} ${post.user.profile.lastName}</h3>
                                                            </a>
                                                            <span><img src="../../images/clock.png" alt=""><fmt:formatDate
                                                                    dateStyle="long" value="${post.creationDate}"/></span>
                                                                <span>
                                                                    | <c:out value="${utils.parseLocation(post.location)}"/>
                                                                </span>
                                                        </div>
                                                    </div>
                                                    <span class="badge badge-info float-right"><c:out value="${post.postCategory.title}"/></span>
                                                </div>
                                                <div class="epi-sec">
                                                    &nbsp
                                                </div>
                                                <div class="job_descp">
                                                    <p>${post.text}</p>
                                                </div>
                                                <c:if test="${post.photo.length() >3}">
                                                    <div class="job_descp">
                                                        <img src="<c:url value='/media/post/${post.photo}' />"/>
                                                    </div>
                                                </c:if>
                                                <c:if test="${post.video.length() >3}">
                                                    <div class="job_descp">
                                                            <%--                                                    <img src="<c:url value='/media/post/${post.video}' />"/>--%>
                                                        <video width="100%" controls>
                                                            <source src="/media/post/${post.video}" type="video/mp4">
                                                            <spring:message code="index.video" text="Your browser does not support HTML5 video."/>
                                                        </video>
                                                    </div>


                                                </c:if>

                                                <div class="job-status-bar">
                                                    <ul class="like-com">
                                                        <li>
                                                            <c:set var="count" value="${0}"/>
                                                            <c:set var="likeid" value="${0}"/>
                                                            <c:forEach items="${post.likes}" var="like">
                                                                <c:if test="${like.user.id == currentUser.id}">
                                                                    <c:set var="count" value="${count+1}"/>
                                                                    <c:set var="likeid" value="${like.id}"/>
                                                                </c:if>
                                                            </c:forEach>
                                                            <a href="javascript:;" class="addlike ${post.id}-likes-wrap ${count == 0 ? 'not-active' : 'active'}" data-likeid="${likeid}" data-id="${post.id}" data-post="${post}"><i class="fas fa-heart"></i><span class="${post.id}-likes">${post.likeCount}</span> <spring:message code="index.like" text="Like"/></a>
                                                        </li>
                                                        <li>
                                                            <a href="javascript:;" class="addcomment " data-id="${post.id}" data-post="${post}"><i class="fas fa-comment-alt"></i><span class="${post.id}-comments">${post.commentCount}</span> <spring:message code="index.comment" text="Comment"/> </a>

                                                        </li>
                                                    </ul>

                                                </div>

                                                <div>
                                                    <form class="post-comment" data-id="${post.id}" data-post="${post}">
                                                        <div class="cp-field">
                                                            <div class="cpp-fiel">
                                                                <spring:message code="index.commentPlaceholder" text="write your comment here" var="commentPlaceholder"/>
                                                        <input type="text" name="text" class="comment-text ${post.id}-text" placeholder="${commentPlaceholder}" required   />
                                                            </div>
                                                        </div>
                                                        <input type="submit" class="comment-submit" value="Submit"
                                                               style="display: none">
                                                    </form>
                                                </div>

                                                <div class="job-status-bar">
                                                    <ul class="comments-list ${post.id}-commentlist">
                                                        <c:forEach items="${post.comments}" var="comment">
                                                            <li> ${comment.text}</li>
                                                        </c:forEach>
                                                    </ul>
                                                </div>
                                            </div>
                                            <!--post-bar end-->
                                        </c:forEach>
                                    </div>

                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

    </div>

    <div class="container">
            <div class="row justify-content-md-center">
                <div class="col-6">



                </div>
            </div>
        </div>

</div><!--theme-layout end-->



<jsp:include page="layout/footerScript.jsp"/>
</body>
<script type="text/javascript" src="/js/postAndUserManagement_Aser.js"></script>
</html>