package com.ale.blog.controller.view;

import com.ale.blog.entity.User;
import com.ale.blog.handler.utils.UserUtil;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping
public class HomeController {
    @GetMapping
    public String page() {
        return "redirect:home";
    }

    @GetMapping("home")
    public String homePage(Authentication authentication, Model model) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        model.addAttribute("user", userOptional.orElse(null));
        return "home";
    }
}
