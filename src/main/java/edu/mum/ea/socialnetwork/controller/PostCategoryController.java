package edu.mum.ea.socialnetwork.controller;

import edu.mum.ea.socialnetwork.domain.Post;
import edu.mum.ea.socialnetwork.domain.PostCategory;
import edu.mum.ea.socialnetwork.domain.User;
import edu.mum.ea.socialnetwork.services.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/postCategories")
public class PostCategoryController {

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/")
    public ModelAndView getPostCategoryList() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("postCategories", postCategoryService.findAll());
        mav.setViewName("postCategories");
        return mav;
    }

    @PostMapping(value = "/")
    public String addPostCategory(@RequestBody PostCategory postCategory) {
        postCategoryService.add(postCategory);
        return messageSource.getMessage("PostCategory.add", null, LocaleContextHolder.getLocale());
    }

    @DeleteMapping(value = "/")
    public void deleteUnhealthyWord(@RequestBody long id) {
        postCategoryService.delete(id);
    }
}
