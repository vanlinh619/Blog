package com.ale.broadcast.service;

import com.ale.blog.entity.User;
import com.ale.blog.repository.UserRepository;
import com.ale.blog.service.ImageService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl extends com.ale.blog.service.UserServiceImpl implements UserService, Serializable {

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ImageService imageService) {
        super(userRepository, passwordEncoder, imageService);
    }

    @Override
    public Optional<User> findByUuid(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public User loadUserByOidcUser(OidcUser oidcUser) {
        String email = (String) oidcUser.getAttributes().get("email");
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("UnAuthorize"));
    }
}
