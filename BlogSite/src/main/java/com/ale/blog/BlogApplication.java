package com.ale.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.SpringServletContainerInitializer;

@SpringBootApplication
public class BlogApplication extends SpringServletContainerInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }
}
