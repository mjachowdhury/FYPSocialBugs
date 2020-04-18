package edu.mum.ea.socialnetwork.controller;

import edu.mum.ea.socialnetwork.config.RabbitMQDirectConfig;
import edu.mum.ea.socialnetwork.domain.Messages;
import edu.mum.ea.socialnetwork.domain.Post;
import edu.mum.ea.socialnetwork.domain.Profile;
import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.services.PostService;
import edu.mum.ea.socialnetwork.services.FileUploadService;
import edu.mum.ea.socialnetwork.services.UserService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    UserService userService;

    @ModelAttribute("currentUser")
    public Profile profilePic(Principal principal){
        User user = userService.findUserByName(principal.getName());
        return user.getProfile();
    }



    @GetMapping(value = "/post")
    public String post(@ModelAttribute("addPost") Post post, Model model, Principal principal) {
        List<Post> allPost = postService.findPost();

        //To get user ID
        User currentUser = userService.findUserByName(principal.getName());

        model.addAttribute("allPost", allPost);
        model.addAttribute("userId", currentUser.getId());
        System.out.println("POST PAGE: " + allPost.size());

        return "post";
    }

    @PostMapping(value = "/post")
    public String addPost(@ModelAttribute("addPost") Post post, BindingResult result, @RequestParam("imageFile") MultipartFile imageFile,
                          @RequestParam("videoFile") MultipartFile videoFile, RedirectAttributes redirectAttributes, Principal principal) {
        if (result.hasErrors()) {
            return "/post";
        }

        if (!imageFile.isEmpty()) {
            try {
                String imagePath = fileUploadService.saveImage(imageFile);
                post.setPhoto(imagePath);
                //redirectAttributes.addFlashAttribute("savedFile", imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!videoFile.isEmpty()) {
            try {
                String videoPath = fileUploadService.saveImage(videoFile);
                post.setVideo(videoPath);
                //redirectAttributes.addFlashAttribute("savedFile", videoPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(post.getPostCategory() == null) {
            System.out.println("Error: Identified null category.");
        }
        System.out.println(principal.getName());

        User currentUser = userService.findUserByName(principal.getName());

        System.out.println(currentUser.getId());
        System.out.println(currentUser.getUsername());

        post.setUser(currentUser);
        post.setEnabled(true);

        postService.save(post);
        return "redirect:/";
    }


    @GetMapping("/post/search")
    public String postSearch(@RequestParam("search")String word, Model model){
        System.out.println(word);
        model.addAttribute("allPost", postService.searchPosts(word));
        return "search";
    }


}
