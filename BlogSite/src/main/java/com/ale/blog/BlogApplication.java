package com.ale.blog;

import com.ale.blog.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.SpringServletContainerInitializer;

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
