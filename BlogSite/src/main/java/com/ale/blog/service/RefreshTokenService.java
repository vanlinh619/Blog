package com.ale.blog.service;


import com.ale.blog.entity.RefreshToken;
import com.ale.blog.entity.User;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.pojo.response.AccessTokenResponse;
import com.ale.blog.handler.mapper.pojo.request.RefreshTokenInput;

public interface RefreshTokenService {
    AccessTokenResponse createAccessToken(RefreshTokenInput refreshTokenInput) throws AppException;
    RefreshToken createRefreshToken(User user);
}
