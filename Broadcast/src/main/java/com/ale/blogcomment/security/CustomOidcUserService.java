package com.ale.blogcomment.security;

import com.ale.blog.entity.User;
import com.ale.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class CustomOidcUserService extends com.ale.blog.security.CustomOidcUserService {
    public CustomOidcUserService(UserService userService) {
        super(userService);
    }
}
