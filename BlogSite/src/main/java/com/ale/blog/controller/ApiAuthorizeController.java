package com.ale.blog.controller;

import com.ale.blog.entity.RefreshToken;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.*;
import com.ale.blog.handler.mapper.pojo.response.AccessTokenResponse;
import com.ale.blog.handler.mapper.pojo.request.AuthRequest;
import com.ale.blog.handler.mapper.pojo.request.RefreshTokenInput;
import com.ale.blog.handler.mapper.pojo.response.UserView;
import com.ale.blog.security.TokenProvider;
import com.ale.blog.security.UserAccess;
import com.ale.blog.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/public")
@AllArgsConstructor
public class ApiAuthorizeController {

    private final AuthenticationProvider authenticationProvider;
    private final TokenProvider jwtTokenProvider;
    private final RefreshTokenService refreshTokenService;
    private final ModelMapper mapper;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request) {
        try {

            Authentication authenticate = authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()
                    )
            );

            UserAccess userAccess = (UserAccess) authenticate.getPrincipal();
            String token = jwtTokenProvider.generateToken(userAccess);
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userAccess.getUser());

            UserView userView = UserMapper.getInstance().toUserView(userAccess.getUser(),token, refreshToken.getToken(), mapper);
            return ResponseEntity.ok().body(userView);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenInput refreshTokenInput) {
        try {
            AccessTokenResponse accessTokenResponse = refreshTokenService.createAccessToken(refreshTokenInput);
            return ResponseEntity.ok().body(accessTokenResponse);
        } catch (AppException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}