package com.ale.blog.controller;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.security.UserAccess;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("test")
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@AllArgsConstructor
public class TestController {
    @GetMapping
    public String test() {
        UserAccess userAccess = (UserAccess) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(userAccess.getUser().getEmail());
        return "404";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({InsufficientAuthenticationException.class})
    public String handleValidationExceptions(InsufficientAuthenticationException e) {
        e.printStackTrace();
        return "redirect:login";
    }
}
