<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<jsp:useBean id="utils" class="edu.mum.ea.socialnetwork.util.Utils"/>

<security:csrfMetaTags/>
<html>

<head>
    <jsp:include page="layout/head.jsp"/>
    <%--    <script type="text/javascript" src="<c:url value='/js/scroll.js' />"></script>--%>
    <style type="text/css">
        ul.comments-list li {
            padding: 10px;
            margin: 0;
            list-style: none;
            border-bottom: 1px solid #ddd;
            font-size: 14px;
            display: block;
            background: white;
            color: #696868;
        }

        .cp-field {
            float: left;
            width: 100%;
            margin-top: 29.4px;
            padding: 0 0px;
        }

        .cp-field input {
            height: 40px;
            padding: 0 10px;
        }

        .follow {
            background-color: white;
            margin:10px;
        }

    </style>
</head>

<body>

<div class="wrapper">
    <jsp:include page="layout/navbar.jsp"/>

    <main>
        <div class="main-section">
            <div class="container">
                <div class="main-section-data">
                    <div class="row">


                        <%--Left Sidebar--%>
                        <div class="col-lg-3 col-md-4 pd-left-none no-pd">
                            <div class="main-left-sidebar no-margin">
                                <c:if test="${currentUser != null}">
                                <div class="user-data full-width">
                                    <div class="user-profile">
                                        <div class="username-dt">
                                            <div class="usr-pic">
                                                <c:choose>
                                                    <c:when test="${currentUser.profilePhoto.length()>4}">
                                                        <img src="/media/profile/${currentUser.profilePhoto}" alt=""
                                                             height="120px" width="120px">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="<c:url value='/images/user.jpg'/>" alt=""
                                                             height="120px" width="120px">
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div><!--username-dt end-->
                                        <div class="user-specs">
                                            <h3>${currentUser.firstName} ${currentUser.lastName}</h3>
                                            <span>${currentUser.occupation}</span>
                                        </div>
                                    </div><!--user-profile end-->

                                    <ul class="user-fw-status">
                                        <%--<li>
                                            <h4>Following</h4>
                                            <span>34</span>
                                        </li>
                                        <li>
                                            <h4>Followers</h4>
                                            <span>155</span>
                                        </li>--%>
                                        <li>
                                            <a href="<c:url value='/profile/myProfile' />" title="">
                                                <spring:message code="index.viewProfile" text="View Profile" />
                                            </a>
                                        </li>
                                    </ul>
                                </div><!--user-data end-->
                                </c:if>
                                <div class="suggestions full-width">
                                    <div class="sd-title">
                                        <h3><spring:message code="index.suggestions" text="Suggestions"/> </h3>
                                        <%--                                        <i class="la la-ellipsis-v"></i>--%>
                                    </div><!--sd-title end-->
                                    <div id="follow" class="suggestions-list">

                                        <c:forEach var="user" items="${suggestions}">
                                            <div class="suggestion-usd">
                                                <c:choose>
                                                    <c:when test="${user.profilePhoto.length()>4}">
                                                        <img src="/media/profile/${user.profilePhoto}" alt="" height="45px" width="45px">
                                                    </c:when>
                                                    <c:otherwise>
                                                        <img src="<c:url value='/images/user.jpg'/>" alt="" height="45px" width="45px">
                                                    </c:otherwise>
                                                </c:choose>

                                                <div class="sgt-text">
                                                    <h4>${user.firstName}</h4>
                                                    <span>${user.lastName}</span>
                                                </div>
<%--                                                <span data-id="${user.id}"><i class="la la-plus"></i></span>--%>
                                                <span><a href="javascript:" id="${user.id}" class="follow">
                                                    <spring:message code="index.follow" text="Follow"/>
                                                </a></span>

                                            </div>
                                        </c:forEach>

                                    </div><!--suggestions-list end-->
                                </div><!--suggestions end-->

                            </div><!--main-left-sidebar end-->
                        </div>


                        <%--Middle Area--%>


                        <div class="col-lg-6 col-md-8 no-pd">
                            <div class="main-ws-sec">
                                <c:if test="${currentUser != null}">
                                <div class="post-topbar">
                                    <div class="user-picy">
                                        <c:choose>
                                            <c:when test="${currentUser.profilePhoto.length()>4}">
                                                <img src="/media/profile/${currentUser.profilePhoto}" alt=""
                                                     height="50px" width="50px">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="<c:url value='/images/user.jpg'/>" alt="" height="50px"
                                                     width="50px">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="post-st">
                                        <ul>
                                            <li>
                                                <a class="post_project" href="#" title="">
                                                    <spring:message code="index.post" text="Post"/>
                                                </a>
                                            </li>
                                        </ul>
                                    </div><!--post-st end-->
                                </div><!--post-topbar end-->
                                </c:if>
                                <div class="posts-section">

                                    <c:forEach var="post" items="${allPost.content}">
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
                                                        <span><img src="../../images/clock.png" alt=""><fmt:formatDate dateStyle="long" value="${post.creationDate}"/></span>
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
                                            <c:if test="${currentUser != null}">
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
                                            </c:if>
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


                                    <%--                                    <div class="process-comm">--%>
                                    <%--                                        <div class="spinner">--%>
                                    <%--                                            <div class="bounce1"></div>--%>
                                    <%--                                            <div class="bounce2"></div>--%>
                                    <%--                                            <div class="bounce3"></div>--%>
                                    <%--                                        </div>--%>
                                    <%--                                    </div>--%>


                                </div><!--posts-section end-->
                                <c:if test="${currentUser != null}">
                                <div class="load-area" data-id="${currentUser.id}"></div>
                                </c:if>
                                <%--check if there is next page data--%>
                                <c:if test="${nextPage==-1}">
                                    <div class="process-comm">
                                        <h1>No Post yet!</h1>
                                    </div>
                                </c:if>
                                <c:if test="${nextPage==1}">
                                    <div class="process-comm">
                                        <button class="btn btn-danger" id="loadmore"><spring:message code="index.loadMore" text="Load More"/></button>
                                    </div>
                                </c:if>
                            </div><!--main-ws-sec end-->
                        </div>


                        <%--Right Sidebar--%>


                        <div class="col-lg-3 pd-right-none no-pd">
                            <div class="right-sidebar">



                                <div class="widget widget-portfolio">
                                    <div class="wd-heady">
                                        <h3>Advertisement</h3>
                                        <img src="/images/photo-icon.png" alt="">
                                    </div>
                                    <div class="pf-gallery">
<c:forEach items="${ads}" var="ad">
                                        <div>
                                            <p>${ad.text}</p>
                                        </div>
</c:forEach>

                                    </div><!--pf-gallery end-->
                                </div><!--widget-portfolio end-->

                            </div><!--right-sidebar end-->
                        </div>
                    </div>
                </div><!-- main-section-data end-->
            </div>
        </div>
    </main>


    <%--Modal--%>

    <div class="post-popup pst-pj">
        <div class="post-project">
            <h3><spring:message code="index.newPost" text="New Post"/> </h3>
            <div class="post-project-fields mb-3">
                <form:form modelAttribute="addPost" action="post" enctype="multipart/form-data" method="post" name="addPostForm" onsubmit="return validateForm()">

                    <input type="hidden" name="location" id="location"></input>
                    <div class="row">
                        <div class="col-lg-12">
                            <spring:message code="index.newPostPlaceholder" text="Write here" var="newPost"/>
                            <form:textarea path="text" placeholder="${newPost}"></form:textarea>
                        </div>

                        <div class="col-lg-12">
                            <select class="browser-default custom-select" name="postCategory">
                              <option selected value="0">Select post category</option>
                              <c:forEach var="category" items="${postCategories}" varStatus="loop">
                              <option value="${category.id}"><c:out value="${category.title}"/></option>
                              </c:forEach>
                            </select>
                        </div>

                        <div class="col-lg-12">
                            <div class="toggle-btn" style="top: 0;left: 52px;">
                                <div class="custom-control custom-switch">
                                    <form:checkbox  cssClass="custom-control-input" path="notifyAllFollowers" id="notifyAllFollowers" />
                                    <label class="custom-control-label" for="notifyAllFollowers">
                                        <spring:message code="index.notifyAllUser" text="Notify all users"/>
                                    </label>
                                </div>
                            </div>
                            <br><br>
                            <ul>
                                <li>
                                    <button class="active" type="submit" value="post" style="position: relative; z-index:1"><spring:message code="index.post" text="Post"/></button>
                                </li>
                                <div class="add-pic-box">
                                    <div class="row no-gutters">
                                        <div class="col-lg-12 col-sm-12">
                                            <input type="file" name="imageFile" id="image">
                                            <label for="image"><spring:message code="index.selectImage" text="Select Image"/> </label>
                                        </div>
                                    </div>

                                    <div class="row no-gutters">
                                        <div class="col-lg-12 col-sm-12">
                                            <input type="file" name="videoFile" id="video">
                                            <label for="video"><spring:message code="index.selectVideo" text="Select Video"/> </label>
                                        </div>
                                    </div>

                                </div>
                            </ul>
                        </div>

                        <label id="location-label" class="pt-5"></label>
                        <div class="col-lg-12 pt-2">
                            <div class="row">
                                <input type="checkbox" checked class="form-check-input col-1" name="acceptTnC" style="width:17px">
                                <label class="form-check-label col-11 pt-3" for="exampleCheck1">Read <a href="/pages/tandc.html" target="_blank">terms & conditions</a></label>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div><!--post-project-fields end-->

            <a href="#" title=""><i class="la la-times-circle-o"></i></a>
        </div><!--post-project end-->
    </div><!--post-project-popup end-->



</div><!--theme-layout end-->


<!-- Modal -->
<div class="modal fade" id="message-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-body">
        <div>
            <p  class="text-center pt-3 font-weight-bold text-danger" id="errorMsg"></p>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="active float-right" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>
<!-- Modal End -->

<jsp:include page="layout/footerScript.jsp"/>

<%--//for loading more posts--%>
<script src="/js/loadmore.js" type="text/javascript"></script>


<script type="text/javascript">
    $(function () {



        // $(".addlike").click(function(){
        //     var postId = $(this).data("id");
        //     var post = $(this).data("post");
        //     // alert("Like me: " + post);
        //     ajaxSubmitLikes(postId)
        // })
        //
        // function ajaxSubmitLikes(postId) {
        //
        //
        //
        //     $.ajax({
        //         type: "POST",
        //         contentType: "application/json",
        //         url: "/addlike",
        //         data: JSON.stringify(postId),
        //         dataType: 'json',
        //         headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
        //         success: function (data) {
        //             // alert("success");
        //             console.log("SUCCESS : ", data);
        //
        //             var likeIncrement = parseInt($("."+postId+"-likes").html()) + 1;
        //             $("."+postId+"-likes").text(likeIncrement);
        //             console.log("likeIncrement", likeIncrement);
        //
        //         },
        //         error: function (e) {
        //             alert("Really sorry, something went wrong. Please try later")
        //             console.log("ERROR : ", e);
        //         }
        //     });
        //
        // }

        $(".addlike").click(function () {
            var postId = $(this).data("id");
            var post = $(this).data("post");
            // alert("Like me: " + post);
            var isActive = $(this).hasClass("active");
            console.log($(this).hasClass("active"));

            ajaxSubmitLikes(postId, isActive)
        })

        function ajaxSubmitLikes(postId, isActive) {

            var type = isActive ? 'DELETE' : 'POST';
            var finalId = JSON.stringify(postId);
            var contentType = "application/json";
            if (type == "DELETE") {
                var likeId = $("." + postId + "-likes-wrap").data("likeid");
                finalId = {"likeId": likeId, "postId": postId};
                contentType = "application/x-www-form-urlencoded; charset=UTF-8";
            }

            console.log("POST/LIKE ID is: ", postId);

            $.ajax({
                type: type,
                contentType: contentType,
                url: '/like',
                data: finalId,
                dataType: 'json',
                headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                success: function (data) {
                    // alert("success");
                    console.log("SUCCESS : ", data);
                    console.log("type", type);
                    console.log("isActive", isActive);

                    var type = isActive ? 'DELETE' : 'POST';
                    if (type == "POST") {
                        var likeIncrement = parseInt($("." + postId + "-likes").html()) + 1;
                        $("." + postId + "-likes-wrap").data("likeid", data.id);
                        console.log("likeIncrement value inside POST Condition", likeIncrement);
                    } else {
                        var likeIncrement = parseInt($("." + postId + "-likes").html()) - 1;
                        console.log("likeIncrement value inside DELETE Condition", likeIncrement);
                    }

                    $("." + postId + "-likes").text(likeIncrement);
                    console.log("likeIncrement value", likeIncrement);
                    if (!$("." + postId + "-likes-wrap").hasClass("active")) {
                        $("." + postId + "-likes-wrap").addClass("active");
                    } else {
                        $("." + postId + "-likes-wrap").removeClass("active");
                    }

                },
                error: function (e) {
                    alert("Really sorry, something went wrong. Please try later")
                    console.log("ERROR : ", e);
                }
            });

        }

        $(".post-comment").submit(function (e) {
            e.preventDefault();
            var postId = $(this).data("id");
            var post = $(this).data("post");
            // alert("Like me: " + post);
            ajaxSubmitComments(postId)
        })

        function ajaxSubmitComments(postId) {
            console.log("PostID", postId);
            var text = $("." + postId + "-text").val();
            console.log("text", text);
            var commentData = {"postId": postId, "text": text};
            $.ajax({
                type: "POST",
                // contentType: "application/json",
                url: "/addComment",
                // data: JSON.stringify(commentData),
                data: commentData,
                dataType: 'json',
                headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
                success: function (data) {
                    // alert("success");
                    console.log("SUCCESS : ", data);

                    // To increase comment count
                    var commentIncrement = parseInt($("." + postId + "-comments").html()) + 1;
                    $("." + postId + "-comments").text(commentIncrement);
                    console.log("commentIncrement", commentIncrement);

                    //To prepend comment
                    $("." + postId + "-commentlist").prepend("<li>" + text + "</li>");

                    $("." + postId + "-text").val("");

                },
                error: function (e) {
                    alert("Really sorry, something went wrong. Please try later")
                    console.log("ERROR : ", e);
                }
            });
        }

        function getCurrentLocation() {
            $.ajax({
                url: "https://geolocation-db.com/jsonp",
                jsonpCallback: "callback",
                dataType: "jsonp",
                success: function(location) {

                    //$('#country').html(location.country_name);
                    //$('#state').html(location.state);
                    //$('#city').html(location.city);
                    //$('#latitude').html(location.latitude);
                    //$('#longitude').html(location.longitude);
                    //$('#ip').html(location.IPv4);

                    $('#location-label').html(location.city + ', ' + location.state + ', ' + location.country_name)
                    $("#location").val(JSON.stringify(location));
                }
            });
        }
        getCurrentLocation();
    })
</script>



<script src="/js/loadmore.js" type="text/javascript"></script>
<script src="/js/followUnfollow.js" type="text/javascript"></script>
</body>

</html>
