package com.ale.blog.handler.mapper;

import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.response.UserInfoResponse;
import com.ale.blog.handler.mapper.pojo.response.UserView;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

public interface UserMapper extends Mapper{
    default UserView toUserView(User user, String accessToken, String token, ModelMapper mapper) {
        UserView userView = mapper.map(user, UserView.class);
        userView.setAccessToken(accessToken);
        userView.setToken(token);
        return userView;
    }

    UserInfoResponse toUserInfoResponse(User user);
    UserInfoResponse toUserInfoResponseForPostView(User user);
}
