package com.ale.blogcomment.service;

import com.ale.blog.entity.User;
import com.ale.blogcomment.reponsitory.UserRepository;
import com.ale.blogcomment.security.UserAccessDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public Optional<User> findByUuid(UUID uuid) {
        return userRepository.findById(uuid);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AtomicReference<UserDetails> atomicReference = new AtomicReference<>();
        userRepository.findUserByUsername(username).ifPresentOrElse(user -> {
            atomicReference.set(new UserAccessDetails(user));
        }, () -> {
            throw new UsernameNotFoundException(username);
        });
        return atomicReference.get();
    }

    @Override
    public User loadUserByOidcUser(OidcUser oidcUser) {
        String email = (String) oidcUser.getAttributes().get("email");
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new RuntimeException("UnAuthorize"));
    }
}
