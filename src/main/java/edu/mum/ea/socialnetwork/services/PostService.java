package edu.mum.ea.socialnetwork.services;

import edu.mum.ea.socialnetwork.domain.Post;
import edu.mum.ea.socialnetwork.domain.PostFilter;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface PostService {
    Post save(Post post);

    List<Post> findPost();

    Post findPostById(Long id);

    List<Post> findAllPostForSpecificUser(Long id);

    Page<Post> allPostsPaged(int pageNo);

    List<Post> searchPosts(String text);

    Page<Post> allFollowingsPostsPaged(Long userId,int pageNo);

    List<Post> findAllByQuery(PostFilter filter);
}
