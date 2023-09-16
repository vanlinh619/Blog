package com.ale.blog.controller;

import com.ale.blog.entity.Post;
import com.ale.blog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
@RequestMapping("post")
public class PostController {
    private final PostService postService;

    @GetMapping("{slug}")
    public String getPost(@PathVariable String slug, Model model) {
        Post post = postService.getPostBySlug(slug);
        model.addAttribute("post", post);
        return "post";
    }
}
