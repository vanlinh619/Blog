package com.ale.broadcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.ale.blog.entity")
public class BroadcastApplication {
    public static void main(String[] args) {
        SpringApplication.run(BroadcastApplication.class, args);
    }
}
