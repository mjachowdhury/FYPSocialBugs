package edu.mum.ea.socialnetwork.services;

import edu.mum.ea.socialnetwork.domain.PostCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostCategoryService {

    List<PostCategory> findAll();

    void add(PostCategory postCategory);

    void delete(long id);
}
