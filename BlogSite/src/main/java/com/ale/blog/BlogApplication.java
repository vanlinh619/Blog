package com.ale.blog;

import com.ale.blog.entity.User;
import com.ale.blog.entity.state.OAuthProvider;
import com.ale.blog.entity.state.UserRole;
import com.ale.blog.handler.mapper.pojo.request.SearchRequest;
import com.ale.blog.service.SearchService;
import com.ale.blog.service.SlugIdService;
import com.ale.blog.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.SpringServletContainerInitializer;
import java.io.IOException;
import java.time.Instant;

@SpringBootApplication
public class BlogApplication extends SpringServletContainerInitializer {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(BlogApplication.class, args);

        UserService userService = context.getBean(UserService.class);
        userService.findDefaultAdmin();

//        SearchService searchService = context.getBean(SearchService.class);
//        searchService.manuallyIndexData();
    }
}
