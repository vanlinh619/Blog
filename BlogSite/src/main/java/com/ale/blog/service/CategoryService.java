package com.ale.blog.service;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.mapper.pojo.request.CategoryRequest;

import java.util.List;

public interface CategoryService extends EntityService<Category, Long>{
    Category createCategory(CategoryRequest categoryRequest, User author);
    List<Category> getAllLevelByUser(CategoryLevel level, User author);
    Category getCategoryBySlugAndUsername(String slug, String username);
    Category getCategoryBySlugAndAuthor(String slug, User author);
    Category getCategoryByIdAndAuthor(Long id, User author);
}
