package com.ale.blog.handler.mapper.pojo.response;

import com.ale.blog.entity.state.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserView {
    private UUID uuid;
    private String username;
    private UserRole role;
    private String accessToken;
    private String token;
}
