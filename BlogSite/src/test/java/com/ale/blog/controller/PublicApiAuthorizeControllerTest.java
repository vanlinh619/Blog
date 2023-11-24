package com.ale.blog.controller;

import com.ale.blog.config.TestConfig;
import com.ale.blog.entity.User;
import com.ale.blog.handle.TestUtil;
import com.ale.blog.handler.mapper.pojo.request.AuthRequest;
import com.ale.blog.handler.mapper.pojo.request.RefreshTokenInput;
import com.ale.blog.handler.mapper.pojo.response.AccessTokenResponse;
import com.ale.blog.handler.mapper.pojo.response.UserView;
import com.ale.blog.repository.UserRepository;
import com.ale.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Import(TestConfig.class)
public class PublicApiAuthorizeControllerTest {
    @Autowired
    private TestUtil testUtil;
    @Autowired
    ModelMapper mapper;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    void setup() {
        testUtil.createAdminUser();
    }

    @DisplayName("Test login and get refresh token")
    @Test
    void loginTest() throws Exception {
        String jsonBody = TestUtil.toJson(
                AuthRequest.builder()
                        .username(TestUtil.USERNAME)
                        .password(TestUtil.PASSWORD)
                        .build()
        );

        MvcResult mvcResult = mvc.perform(post("/api/public/login")
                        .secure(true)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isOk())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        UserView userView = new ObjectMapper().readValue(json, UserView.class);
        Assertions.assertEquals(userView.getUsername(), TestUtil.USERNAME, "Username must be matches");

        /*get new token*/
        String jsonToken = TestUtil.toJson(RefreshTokenInput.builder()
                .token(userView.getToken())
                .build());

        mvcResult = mvc.perform(post("/api/public/refresh")
                        .secure(true)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonToken))
                .andExpect(status().isOk())
                .andReturn();

        json = mvcResult.getResponse().getContentAsString();
        AccessTokenResponse accessTokenResponse = TestUtil.readObject(json, AccessTokenResponse.class);
        Assertions.assertNotNull(accessTokenResponse.getAccessToken());
    }

    @DisplayName("Test login password incorrect")
    @Test
    void loginFailedTest() throws Exception {
        String jsonBody = TestUtil.toJson(
                AuthRequest.builder()
                        .username(TestUtil.USERNAME)
                        /*Password incorrect*/
                        .password("1")
                        .build()
        );

        MvcResult mvcResult = mvc.perform(post("/api/public/login")
                        .secure(true)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBody))
                .andExpect(status().isUnauthorized())
                .andReturn();

        String json = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals(json, "");
    }

    @AfterEach
    void clearDB() {
        testUtil.cleanUser();
    }
}
