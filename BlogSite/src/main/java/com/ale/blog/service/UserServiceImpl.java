package com.ale.blog.service;

import com.ale.blog.entity.Image;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.ImageType;
import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.request.UserInfoRequest;
import com.ale.blog.handler.mapper.pojo.response.DataResponse;
import com.ale.blog.handler.mapper.pojo.response.state.MessageCode;
import com.ale.blog.handler.mapper.pojo.response.state.Status;
import com.ale.blog.repository.UserRepository;
import com.ale.blog.security.UserAccessDetails;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
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
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    protected final UserRepository userRepository;
    protected final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    @Value("${account.admin.username}")
    private String username;
    @Value("${account.admin.password}")
    private String password;

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
    public Optional<User> findDefaultAdmin() {
        return userRepository.findFirstByRole(UserRole.ADMIN)
                .or(() -> {
                    User userAD = User.builder()
                            .username(username)
                            .password(password)
                            .lastName(username)
                            .firstName(username)
                            .email(username+"@mail.com")
                            .registered(Instant.now())
                            .role(UserRole.ADMIN)
                            .provider(OAuthProvider.LOCAL)
                            .build();
                    create(userAD);
                    return Optional.of(userAD);
                });
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
        return userRepository.findUserByUsername(username);
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
        return userRepository.findUserByUsername(username).orElseThrow(() -> new AppException(DataResponse.builder()
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

    @Transactional(rollbackOn = {Exception.class})
    @Override
    public User updateInfo(@Nonnull User user, @Nonnull UserInfoRequest userInfoRequest) {
        user.setFullName(userInfoRequest.getFullName());
        user.setStory(userInfoRequest.getStory());
        if(userInfoRequest.getAvatar() != null) {
            Image old = user.getAvatar();
            if (old != null) {
                imageService.deleteAvatar(old.getId());
            }

            Image image = imageService.upsertImage(userInfoRequest.getAvatar(), user, ImageType.AVATAR);
            user.setAvatar(image);
        }
        return userRepository.save(user);
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }
}
