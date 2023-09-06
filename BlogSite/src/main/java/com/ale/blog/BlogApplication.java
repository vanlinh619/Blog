package com.ale.blog;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.SpringServletContainerInitializer;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootApplication
public class BlogApplication extends SpringServletContainerInitializer {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BlogApplication.class, args);

        UserService userService = context.getBean(UserService.class);
        userService.findFistUser().ifPresentOrElse(user -> {}, () -> {
            User userAD = User.builder()
                    .username("admin")
                    .password("admin")
                    .lastName("admin")
                    .firstName("admin")
                    .email("admin@mail.com")
                    .phoneNumber("0999999999")
                    .intro("admin")
                    .registered(Instant.now())
                    .role(UserRole.ADMIN)
                    .build();
            User userCT = User.builder()
                    .username("content")
                    .password("content")
                    .lastName("content creator")
                    .firstName("content creator")
                    .email("content creator@mail.com")
                    .phoneNumber("0888888888")
                    .intro("content creator")
                    .registered(Instant.now())
                    .role(UserRole.CONTENT_CREATOR)
                    .build();
            userService.create(userAD);
            userService.create(userCT);
        });
    }
}
