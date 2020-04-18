package edu.mum.ea.socialnetwork.controller;

import edu.mum.ea.socialnetwork.domain.Post;
import edu.mum.ea.socialnetwork.domain.PostFilter;
import edu.mum.ea.socialnetwork.repository.PostRepository;
import edu.mum.ea.socialnetwork.services.PostCategoryService;
import edu.mum.ea.socialnetwork.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private PostCategoryService postCategoryService;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private MessageSource messageSource;

    @GetMapping(value = "/")
    public ModelAndView getPostCategoryList(@RequestParam("pcid") Optional<Long> postCategoryId, @RequestParam("d") Optional<Integer> creationDateAfter) {
        ModelAndView mav = new ModelAndView();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0); // ! clear would not reset the hour of day !
        cal.clear(Calendar.MINUTE);
        cal.clear(Calendar.SECOND);
        cal.clear(Calendar.MILLISECOND);

        // get start of this week in milliseconds
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date dateAfter = cal.getTime();

        if(creationDateAfter.isPresent()) {
            mav.addObject("d", creationDateAfter.get());

            if(creationDateAfter.get() == 7) {

            } else if(creationDateAfter.get() == 30) {
                cal.set(Calendar.DAY_OF_MONTH, 1);
                dateAfter = cal.getTime();
            } else {
                cal.set(Calendar.MONTH, 0);
                cal.set(Calendar.DAY_OF_MONTH, 1);

                dateAfter = cal.getTime();
            }
        }

        long categoryId = 0L;
        if(postCategoryId.isPresent()) {
            categoryId = postCategoryId.get();

            mav.addObject("pcid", postCategoryId.get());
        }


        List<Post> posts = postService.findAllByQuery(new PostFilter(categoryId, dateAfter));

        mav.addObject("postCategories", postCategoryService.findAll());
        mav.addObject("numberOfPosts", posts.size());
        mav.addObject("totalNumberOfPosts", postRepository.countAllBy());
        mav.addObject("posts", posts);

        mav.setViewName("report");
        return mav;
    }
}
