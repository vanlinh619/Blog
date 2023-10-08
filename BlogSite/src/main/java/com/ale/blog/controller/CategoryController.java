package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.pojo.request.PageRequest;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final PostService postService;

    @GetMapping("{username}")
    public String getAllPost(@PathVariable String username, @Valid PageRequest pageRequest, Model model) {
        Page<Post> posts = postService.findAllByUsername(username, QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("posts", posts);
        model.addAttribute("username", username);
        return "category";
    }

    @GetMapping("{categoryUrl}/{username}")
    public String getAllPostOfCategory(@PathVariable String categoryUrl, @PathVariable String username, @Valid PageRequest pageRequest, Model model) {
        Category category = categoryService.getCategoryBySlugAndUsername(categoryUrl, username);
        Page<Post> posts = postService.findAllByCategory(category, QueryRequest.builder()
                .page(pageRequest.getPage())
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("posts", posts);
        model.addAttribute("username", username);
        return "category";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
