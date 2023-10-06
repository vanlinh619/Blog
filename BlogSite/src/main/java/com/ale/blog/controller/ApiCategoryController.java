package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.CategoryMapper;
import com.ale.blog.handler.mapper.pojo.request.CategoryRequest;
import com.ale.blog.handler.mapper.pojo.response.CategoryResponse;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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

    @GetMapping ("{uuid}")
    public List<CategoryResponse> getAllCategory(@PathVariable String uuid) {
        User author = userService.getById(UUID.fromString(uuid));
        List<Category> categories = categoryService.getAllLevelByUser(CategoryLevel.LEVEL_1, author);
        return categoryMapper.toCategoryResponses(categories);
    }

    @PostMapping
    public DataResponse createCategory(@Valid @RequestBody CategoryRequest categoryRequest){
        Category category = categoryService.createCategory(categoryRequest);
        return DataResponse.builder()
                .id(category.getId())
                .status(DataResponse.ResponseStatus.CREATED)
                .build();
    }
}
