package com.ale.blog.handler.mapper;

import com.ale.blog.entity.Category;
import com.ale.blog.handler.mapper.pojo.response.CategoryResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class CategoryMapperImpl implements CategoryMapper {
    private final ModelMapper mapper;

    @Override
    public List<CategoryResponse> toCategoriesResponses(List<Category> categories) {
        return categories.stream()
                .map(category -> mapper.map(category, CategoryResponse.class))
                .toList();
    }

    @Override
    public CategoryResponse toCategoryResponses(Category category) {
        return mapper.map(category, CategoryResponse.class);
    }

    @Override
    public CategoryResponse toCategoryResponseForPostView(Category category) {
        return CategoryResponse.builder()
                .slug(category.getSlug())
                .title(category.getTitle())
                .build();
    }


}
