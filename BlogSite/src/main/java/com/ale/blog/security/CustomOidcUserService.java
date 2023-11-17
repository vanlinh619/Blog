package com.ale.blog.security;

import com.ale.blog.entity.User;
import com.ale.blog.security.UserOAuth2AccessDetail;
import com.ale.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomOidcUserService extends OidcUserService {
    private final UserService userService;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        User user = userService.loadUserByOidcUser(oidcUser);
        return new UserOAuth2AccessDetail(oidcUser, user);
    }
}
