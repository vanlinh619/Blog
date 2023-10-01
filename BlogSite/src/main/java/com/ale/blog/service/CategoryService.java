package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.mapper.request.CategoryRequest;

import java.util.List;

public interface CategoryService extends EntityService<Category, Long>{
    Category createCategory(CategoryRequest categoryRequest);
    List<Category> getAllLevelByUser(CategoryLevel level, User user);
    Category getCategory(Long id);

    /**
     * Always hava default category = all
     * */
    Category getDefaultCategory();
}
