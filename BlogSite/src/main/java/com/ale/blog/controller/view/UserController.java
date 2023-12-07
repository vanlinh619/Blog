package com.ale.blog.controller.view;

import com.ale.blog.entity.Post;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.PostState;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.QueryRequest;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.utils.SortType;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.handler.utils.UserUtil;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.ImageService;
import com.ale.blog.service.PostService;
import com.ale.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("profile")
public class UserController {
    private final UserService userService;
    private final ImageService imageService;
    private final PostService postService;

    @GetMapping("{username}")
    public String goToProfile(Authentication authentication, @PathVariable String username, Model model) {
        Optional<User> userOptional = UserUtil.owner(authentication);

        User user = userService.getByUsername(username);
        model.addAttribute("user", user);
        userOptional
                .map(owner -> {
                    model.addAttribute("owner", owner);
                    return owner;
                })
                .flatMap(imageService::getAvatar)
                .ifPresent(image -> {
                    model.addAttribute("avatar", image);
                });
        return "profile";
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e, Model model) {
        e.printStackTrace();
        if (e instanceof AppException appException && appException.getResponse().getCode() == MessageCode.UN_AUTHORIZE) {
            model.addAttribute("message", "Un Authorize");
        } else {
            model.addAttribute("message", "Page not found");
        }
        return "404";
    }
}
