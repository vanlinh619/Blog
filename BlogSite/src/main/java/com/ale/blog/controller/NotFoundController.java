package com.ale.blog.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotFoundController implements ErrorController {
    @RequestMapping("404")
    public String getPage404() {
        return "404";
    }
}

