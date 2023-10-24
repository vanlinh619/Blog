package com.ale.blog.controller.view;

import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.utils.UtilMethod;
import com.ale.blog.security.UserAccess;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@AllArgsConstructor
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@RequestMapping("profile")
public class UserController {
    @GetMapping()
    public String goToProfile(Authentication authentication, Model model) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        model.addAttribute("user", userAccess.getUser());
        return "profile";
    }

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler({Exception.class})
    public String handleValidationExceptions(Exception e) {
        e.printStackTrace();
        return "404";
    }
}
