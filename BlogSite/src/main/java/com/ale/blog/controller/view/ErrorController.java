package com.ale.blog.controller.view;

import com.ale.blog.entity.User;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    private final ImageService imageService;

    @RequestMapping("404")
    public String getPage404(Authentication authentication, Model model) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        userOptional
                .map(user -> {
                    model.addAttribute("user", user);
                    return user;
                })
                .flatMap(imageService::getAvatar)
                .ifPresent(image -> {
                    model.addAttribute("avatar", image);
                });
        model.addAttribute("message", "Page not found");
        return "404";
    }

    @RequestMapping("un-authorize")
    public String getPageUnAuthorize(Authentication authentication, Model model) {
        Optional<User> userOptional = UserUtil.owner(authentication);
        userOptional
                .map(user -> {
                    model.addAttribute("user", user);
                    return user;
                })
                .flatMap(imageService::getAvatar)
                .ifPresent(image -> {
                    model.addAttribute("avatar", image);
                });
        model.addAttribute("message", "Un Authorize");
        return "404";
    }
}

