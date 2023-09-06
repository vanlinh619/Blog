package com.ale.blog.handler.mapper;

import com.ale.blog.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    static UserMapper getInstance() {
        return new UserMapper() {
        };
    }

    default UserView toUserView(User user, String accessToken, String token, ModelMapper mapper) {
        UserView userView = mapper.map(user, UserView.class);
        userView.setAccessToken(accessToken);
        userView.setToken(token);
        return userView;
    }
}
