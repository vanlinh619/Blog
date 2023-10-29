package com.ale.blog.service;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.CategoryRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.handler.utils.StaticVariable;
import com.ale.blog.repository.UserRepository;
import com.ale.blog.security.UserAccessDetails;
import lombok.AllArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AtomicReference<UserDetails> atomicReference = new AtomicReference<>();
        userRepository.findFirstByUsername(username).ifPresentOrElse(user -> {
            atomicReference.set(new UserAccessDetails(user));
        }, () -> {
            throw new UsernameNotFoundException(username);
        });
        return atomicReference.get();
    }

    @Override
    public Optional<UserDetails> loadUserByUuid(UUID uuid) {
        AtomicReference<Optional<UserDetails>> atomicReference = new AtomicReference<>();
        userRepository.findByUuid(uuid).ifPresentOrElse(user -> {
            atomicReference.set(
                    Optional.of(new UserAccessDetails(user))
            );
        }, () -> {
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }

    /**
     * throw AppException if can't find id
     */
    @Override
    public User getById(UUID uuid) {
        return defaultGetById(uuid, userRepository);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Override
    public Optional<User> findFistUser() {
        return userRepository.findFirstByOrderByUuid();
    }

    @Override
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        userRepository.save(user);
        return user;
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findFirstByUsername(username).orElseThrow(() -> new AppException(DataResponse.builder()
                .status(Status.FAILED)
                .code(MessageCode.NOT_FOUND)
                .message(username)
                .build())
        );
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User loadUserByOidcUser(OidcUser oidcUser) {
        String email = (String) oidcUser.getAttributes().get("email");
        return userRepository.findUserByEmail(email).orElseGet(() -> {
            User user = User.builder()
                    .username(RandomString.make(18))
                    .password(RandomString.make(18))
                    .firstName((String) oidcUser.getAttributes().get("family_name"))
                    .lastName((String) oidcUser.getAttributes().get("given_name"))
                    .email(email)
                    .registered(Instant.now())
                    .role(UserRole.USER)
                    .provider(OAuthProvider.GOOGLE)
                    .build();
            user.setFullName(user.getFirstName() + " " + user.getLastName());
            userRepository.save(user);
            return user;
        });
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
