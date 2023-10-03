package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.request.PageRequest;
import com.ale.blog.handler.mapper.request.QueryRequest;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;
    private final PostService postService;

    @GetMapping("{username}")
    public String getAllPost(@PathVariable String username, @Valid PageRequest pageRequest, Model model) {
        List<Post> posts = postService.findAllByUsername(username, QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                        .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("posts", posts);
        return "category";
    }

    @GetMapping("{username}/{category}")
    public String getAllPostOfCategory(@PathVariable String username, @PathVariable String category, @Valid PageRequest pageRequest, Model model) {
        List<Post> posts = postService.findAllByUsername(username, QueryRequest.builder()
                .page(pageRequest.getPage() - 1)
                .size(StaticVariable.PAGE_SIZE)
                .sortBy(Post.Fields.createDate)
                .sortType(SortType.DESC.name())
                .build());
        model.addAttribute("posts", posts);
        return "category";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({MethodArgumentNotValidException.class, AppException.class})
    public String handleValidationExceptions(Exception ex) {
        return "404";
    }
}
