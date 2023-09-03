package com.ale.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("post-article")
public class PostArticleController {

    @GetMapping
    public String postArticle() {
        return "post-article";
    }
}
