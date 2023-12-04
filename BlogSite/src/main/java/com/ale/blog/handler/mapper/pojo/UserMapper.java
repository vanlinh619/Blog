package com.ale.blog.handler.mapper.pojo;

import com.ale.blog.entity.User;
import com.ale.blog.handler.mapper.Mapper;
import com.ale.blog.handler.mapper.pojo.response.UserInfoResponse;

public interface UserMapper extends Mapper {
    UserInfoResponse toUserInfoResponse(User user);
}
