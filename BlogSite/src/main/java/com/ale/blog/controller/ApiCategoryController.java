package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.CategoryMapper;
import com.ale.blog.handler.mapper.pojo.request.CategoryRequest;
import com.ale.blog.handler.mapper.pojo.response.CategoryResponse;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR})
@AllArgsConstructor
@RequestMapping("api/authorize/category")
public class ApiCategoryController {
    private final CategoryService categoryService;
    private final UserService userService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public List<CategoryResponse> getAllCategory(Authentication authentication) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        List<Category> categories = categoryService.getAllLevelByUser(CategoryLevel.LEVEL_1, userAccess.getUser());
        return categoryMapper.toCategoryResponses(categories);
    }

    @PostMapping
    public DataResponse createCategory(Authentication authentication, @Valid @RequestBody CategoryRequest categoryRequest){
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        Category category = categoryService.createCategory(categoryRequest, userAccess.getUser());
        return DataResponse.builder()
                .id(category.getId())
                .status(DataResponse.ResponseStatus.CREATED)
                .build();
    }
}
