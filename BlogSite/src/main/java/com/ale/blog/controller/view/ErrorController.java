package com.ale.blog.controller.view;

import com.ale.blog.handler.utils.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @RequestMapping("404")
    public String getPage404(Authentication authentication, Model model) {
        model.addAttribute("user", UserUtil.authenticated(authentication));
        model.addAttribute("message", "Page not found");
        return "404";
    }

    @RequestMapping("un-authorize")
    public String getPageUnAuthorize(Authentication authentication, Model model) {
        model.addAttribute("user", UserUtil.authenticated(authentication));
        model.addAttribute("message", "Un Authorize");
        return "404";
    }
}

