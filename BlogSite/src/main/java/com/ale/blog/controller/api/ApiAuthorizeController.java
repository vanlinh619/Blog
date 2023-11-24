package com.ale.blog.controller.api;

import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.security.JwtTokenProvider;
import com.ale.blog.security.UserAccess;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RolesAllowed({UserRole.Fields.ADMIN, UserRole.Fields.CONTENT_CREATOR, UserRole.Fields.USER})
@RequestMapping(path = "api/authorize/token")
@AllArgsConstructor
public class ApiAuthorizeController {
    private final JwtTokenProvider tokenProvider;

    @GetMapping
    public DataResponse getAccessToken(Authentication authentication) {
        UserAccess userAccess = (UserAccess) authentication.getPrincipal();
        String token = tokenProvider.generateToken(userAccess.getUser(), 60000L);
        return DataResponse.builder()
                .status(Status.SUCCESS)
                .code(MessageCode.SUCCESS)
                .data(token)
                .build();
    }
}
