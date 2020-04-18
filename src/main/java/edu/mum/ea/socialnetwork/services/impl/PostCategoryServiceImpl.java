package edu.mum.ea.socialnetwork.services.impl;

import edu.mum.ea.socialnetwork.domain.PostCategory;
import edu.mum.ea.socialnetwork.repository.PostCategoryRepository;
import edu.mum.ea.socialnetwork.services.PostCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class PostCategoryServiceImpl implements PostCategoryService {
    @Autowired
    private PostCategoryRepository postCategoryRepository;

    @Override
    public List<PostCategory> findAll() {
        return postCategoryRepository.findAllByOrderByListOrderAsc();
    }

    @Override
    public void add(PostCategory postCategory) {
        postCategory.setTitle(postCategory.getTitle().trim());
        postCategoryRepository.save(postCategory);
    }

    @Override
    public void delete(long id) {
        postCategoryRepository.deleteById(id);
    }
}
