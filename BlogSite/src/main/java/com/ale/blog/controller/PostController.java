package com.ale.blog.controller;

import com.ale.blog.entity.Category;
import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.CategoryLevel;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("post")
public class PostController {
    private final PostService postService;
    private final CategoryService categoryService;

    @GetMapping("{slug}")
    public String getPost(@PathVariable String slug, Model model) {
        Post post = postService.getPostBySlug(slug);
        User user = post.getAuthor();
        List<Category> categories = categoryService.getAllLevelByUser(CategoryLevel.LEVEL_1, user);
        model.addAttribute("post", post);
        model.addAttribute("categories", categories);
        return "post";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
