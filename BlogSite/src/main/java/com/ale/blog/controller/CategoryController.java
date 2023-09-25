package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.request.CategoryRequest;
import com.ale.blog.handler.mapper.response.DataResponse;
import com.ale.blog.service.CategoryService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@AllArgsConstructor
@RequestMapping("api/authorize/category")
public class CategoryController {
    private final CategoryService service;

    @PostMapping
    public DataResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        Category category = service.createCategory(categoryRequest);
        return DataResponse.builder()
                .id(category.getId())
                .status(DataResponse.ResponseStatus.CREATED)
                .build();
    }
}
