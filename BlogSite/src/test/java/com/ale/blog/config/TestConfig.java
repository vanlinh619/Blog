package com.ale.blog.config;

import com.ale.blog.handle.TestUtil;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {
    @Bean
    public TestUtil getTestUtil() {
        return new TestUtil();
    }
}
