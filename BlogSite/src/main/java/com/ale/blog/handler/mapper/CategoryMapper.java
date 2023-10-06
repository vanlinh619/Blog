package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Category;
import com.ale.blog.handler.mapper.pojo.response.CategoryResponse;

import java.util.List;

public interface CategoryMapper extends Mapper{
    List<CategoryResponse> toCategoryResponses(List<Category> categories);
}
