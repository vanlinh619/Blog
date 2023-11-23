package com.ale.blogcomment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.ale.blog.entity")
public class BroadcastApplication {
    public static void main(String[] args) {
        SpringApplication.run(BroadcastApplication.class, args);
    }
}
