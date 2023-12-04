package com.ale.blog.handler.mapper.pojo;

import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.pojo.response.UserInfoResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final ModelMapper modelMapper;

    public UserInfoResponse toUserInfoResponse(User user) {
        UserInfoResponse userInfoResponse = modelMapper.map(user, UserInfoResponse.class);
        if (user.getAvatar() != null) {
            userInfoResponse.setAvatarId(user.getAvatar().getId().toString());
        }
        return userInfoResponse;
    }
}
