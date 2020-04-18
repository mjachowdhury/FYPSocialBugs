package edu.mum.ea.socialnetwork.controller;

import edu.mum.ea.socialnetwork.domain.Notification;
import edu.mum.ea.socialnetwork.domain.Post;
import edu.mum.ea.socialnetwork.domain.Profile;
import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ProfileService profileService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AdvertisementService advertisementService;

    @Autowired
    PostCategoryService postCategoryService;

    @ModelAttribute("currentUser")
    public Profile currentUser(Principal principal){
        if(principal != null) {
            User user = userService.findUserByName(principal.getName());
            return user.getProfile();
        }
        return null;
    }

    @GetMapping("/")
    public String post(@ModelAttribute("addPost") Post post, Model model, Principal principal) {
        Page<Post> posts = null;
        List<Profile> suggestions = new ArrayList<>();
        List<Notification> notifications = new ArrayList<>();

        if(principal != null) {
            posts = postService.allFollowingsPostsPaged(currentUser(principal).getId(),0);

            suggestions = profileService.findTop5(currentUser(principal).getId());

            User user = userService.findUserByName(principal.getName());
            notifications = notificationService.findNotificationByUserId(user.getId());
            System.out.println("All Notifications"+ notifications.size());
        } else {
            posts = postService.allFollowingsPostsPaged(null,0);
        }
        if(posts.isEmpty()){
            model.addAttribute("nextPage", -1);
        }else if(posts.getContent().size()<5){
            model.addAttribute("nextPage",0);
        }else{
            model.addAttribute("nextPage", 1);
        }

        model.addAttribute("allPost", posts);
        model.addAttribute("suggestions", suggestions);
        model.addAttribute("notifications", notifications);
        model.addAttribute("ads", advertisementService.getAdList());
        model.addAttribute("postCategories", postCategoryService.findAll());

        return "index";
    }

    @GetMapping("/{pageNo}")
    public @ResponseBody Page<Post> getPostsPaged(@PathVariable("pageNo") Integer pageNo){
        System.out.println("-------------getPostPaged Called=---------------------");
        Page<Post> posts = postService.allPostsPaged(pageNo);
        return posts;
    }








//
//    @GetMapping("/error")
//    public String showErrorPage(){
//        return "error";
//    }

    @GetMapping("/index")
    public String showIndex(){
        return "index";
    }

    @GetMapping("/denied")
    public String denied(){
        return "denied";
    }
}
