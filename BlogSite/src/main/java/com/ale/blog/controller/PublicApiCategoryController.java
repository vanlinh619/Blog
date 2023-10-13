package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.mapper.CategoryMapper;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/public/category")
public class PublicApiCategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    private final UserService userService;

    @GetMapping("{username}")
    public DataResponse getAllCategory(@PathVariable String username) {
        User user = userService.getByUsername(username);
        List<Category> categories = categoryService.getAllLevelByUser(CategoryLevel.LEVEL_1, user);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(categoryMapper.toCategoriesResponses(categories))
                .build();
    }
}
