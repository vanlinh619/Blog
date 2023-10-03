package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Category;
import com.ale.blog.handler.mapper.response.CategoryResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {
    private final ModelMapper mapper;

    @Override
    public List<CategoryResponse> toCategoryResponses(List<Category> categories) {
        return categories.stream()
                .map(category -> mapper.map(category, CategoryResponse.class))
                .toList();
    }
}
