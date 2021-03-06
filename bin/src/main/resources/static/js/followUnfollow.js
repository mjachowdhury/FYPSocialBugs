$(document).ready(function(){
    $(document).on('click', ".follow", (function(){
        let userId = $(this).attr('id');
        let userClass = $(this).attr('class');
        ajaxSubmitLikes(userId, userClass, name)
    }));

    function ajaxSubmitLikes(userId, userClass, name) {
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/follow",
            data: JSON.stringify(userId),
            dataType: 'json',
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            success: function (data) {
                console.log("SUCCESS : ", data);
                $("a#"+userId).text("Followed");
            },
            error: function (e) {
                alert("You already follow this user!");
            }
        });
    }
});

//this function is for unfollowing the followed users
$(function() {
    $(document).on('click', ".unfollowing", (function(){
        let userId = $(this).attr('id');
        let userClass = $(this).attr('class');
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/unfollow",
            data: JSON.stringify(userId),
            dataType: "json",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            success: function (data) {
                console.log("SUCCESS : ", data);
                $("a#"+userId).text("Un-Followed");
            },
            error: function (e) {
                alert("You are not following this user so you can't un-follow him/her!");
            }
        })
    }))
});

//this function is for instance searching the users
$(function () {
    $("#search").keyup(function(e) {
        let username = $(this).val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/search",
            data: username,
            dataType: "json",
            headers: {"X-CSRF-TOKEN": $("meta[name='_csrf']").attr("content")},
            success: function (data) {
                let result = [];
                $(data).each(function (index, value) {
                    if(value.profilePhoto){
                        result.push(
                            '<div class="col-lg-3 col-md-4 col-sm-6 col-12">' +
                            '<div class="company_profile_info">' +
                            '<div class="company-up-info">' +
                            '<img src="/media/profile/' + value.profilePhoto + '" alt="profile photo">' +
                            '<h3>' + value.firstName + ' ' + value.lastName + '</h3>' +
                            '<h4>' + value.occupation + '</h4>' +
                            '<ul>' +
                            '<li><a href="javascript:" class="follow" id="' + value.id + '">Follow</a></li>' +
                            '</ul>' +
                            '</div>' +
                            '<a href="/profile/' + value.id + '" class="view-more-pro"> View Profile </a>' +
                            '</div>' +
                            '</div>'
                        )
                    }else{
                        result.push(
                            '<div class="col-lg-3 col-md-4 col-sm-6 col-12">' +
                            '<div class="company_profile_info">' +
                            '<div class="company-up-info">' +
                            '<img src="/images/user.jpg" alt="profile photo">' +
                            '<h3>' + value.firstName + ' ' + value.lastName + '</h3>' +
                            '<h4>' + value.occupation + '</h4>' +
                            '<ul>' +
                            '<li><a href="javascript:" class="follow" id="' + value.id + '">Follow</a></li>' +
                            '</ul>' +
                            '</div>' +
                            '<a href="/profile/' + value.id + '" class="view-more-pro"> View Profile </a>' +
                            '</div>' +
                            '</div>'
                        )
                    }

                });
                $(".row").html(result);
            },
            error: function (e) {
                // alert("search not found");
            }
        })
    })
})