package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.handler.mapper.request.QueryRequest;
import com.ale.blog.service.CategoryService;
import com.ale.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService service;
    private final PostService postService;

    @GetMapping("{username}")
    public String getAllPost(@PathVariable String username, Model model) {
        List<Post> posts = postService.findAllByUsername(username, QueryRequest.builder()
                .page(0)
                .size(10)
                .sortBy(Post.Fields.createDate)
                .build());
        model.addAttribute("posts", posts);
        return "category";
    }
}
