package com.ale.blog.controller.view;

import com.ale.blog.handler.utils.UtilMethod;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class NotFoundController implements ErrorController {
    @RequestMapping("404")
    public String getPage404(Authentication authentication, Model model) {
        model.addAttribute("user", UtilMethod.authenticated(authentication));
        return "404";
    }
}

