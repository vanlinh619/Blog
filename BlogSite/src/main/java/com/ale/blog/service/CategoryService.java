package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.mapper.pojo.request.CategoryRequest;

import java.util.List;

public interface CategoryService extends EntityService<Category, Long>{
    Category createCategory(CategoryRequest categoryRequest);
    List<Category> getAllLevelByUser(CategoryLevel level, User user);
    Category getCategoryById(Long id);
    Category getCategoryBySlugAndUsername(String slug, String username);
}
