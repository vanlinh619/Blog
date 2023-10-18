package com.ale.blog.service;

import com.ale.blog.BlogApplication;
import com.ale.blog.entity.User;
import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

//@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class UserServiceTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Mock
    private OidcUser oidcUser;

    public final String USERNAME = "Test";
    public final String PASSWORD = "Test";
    public final String EMAIL = "Test@email.com";


    @BeforeEach
    void setup() {
        Mockito.when(oidcUser.getAttributes()).then(invocationOnMock ->
                Map.of("email", EMAIL + "1", "family_name", USERNAME, "given_name", USERNAME)
        );

        User test = User.builder()
                .username(USERNAME)
                .password(PASSWORD)
                .lastName(USERNAME)
                .firstName(USERNAME)
                .email(EMAIL)
                .phoneNumber("0999999999")
                .intro("test")
                .registered(Instant.now())
                .role(UserRole.ADMIN)
                .provider(OAuthProvider.LOCAL)
                .build();
        userService.create(test);
    }

    @DisplayName("get User")
    @Test
    public void getUser() {
        User testCreated = userService.getByUsername(USERNAME);
        Assertions.assertTrue(passwordEncoder.matches(PASSWORD, testCreated.getPassword()), "Password matches");

        UserDetails userDetails = userDetailsService.loadUserByUsername(USERNAME);
        Assertions.assertEquals(userDetails.getUsername(), USERNAME, "Username matches");

        userService.loadUserByUuid(testCreated.getUuid()).or(() -> {
            Assertions.fail("User must be found");
            return Optional.empty();
        });

        Supplier<Optional<User>> mustBeFound = () -> {
            Assertions.fail("User must be found");
            return Optional.empty();
        };
        userService.findFistUser().or(mustBeFound);
        userService.findUserByEmail(EMAIL).or(mustBeFound);
        userService.findByUsername(USERNAME).or(mustBeFound);
        userService.getById(testCreated.getUuid());

        Assertions.assertThrows(AppException.class, () -> {
            userService.getByUsername("1234");
        }, "Must throw exception");
    }

    @DisplayName("get Oidc User")
    @Test
    public void getOidcUser() {
        User user = userService.loadUserByOidcUser(oidcUser);
        Assertions.assertEquals(user.getEmail(), EMAIL + "1", "User must be found");
    }

    @AfterEach
    void clearDB() {
        userRepository.deleteAll();
    }
}
