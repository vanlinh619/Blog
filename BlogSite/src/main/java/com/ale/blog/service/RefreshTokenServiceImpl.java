package com.ale.blog.service;

import com.ale.blog.entity.RefreshToken;
import com.ale.blog.entity.User;
import com.ale.blog.handler.exception.AppException;
import com.ale.blog.handler.mapper.AccessToken;
import com.ale.blog.handler.mapper.RefreshTokenInput;
import com.ale.blog.repository.RefreshTokenRepository;
import com.ale.blog.security.JwtTokenProvider;
import com.ale.blog.security.UserAccessDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenProvider jwtTokenProvider;
    @Value("${jwt.expiration-refresh}")
    private Long expirationRefresh;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, JwtTokenProvider jwtTokenProvider) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public AccessToken createAccessToken(RefreshTokenInput refreshTokenInput) throws AppException {
        AccessToken accessToken = new AccessToken();
        refreshTokenRepository.findFirstByToken(refreshTokenInput.getToken()).ifPresentOrElse(refreshToken -> {
            String token = jwtTokenProvider.generateToken(new UserAccessDetails(refreshToken.getUser()));
            if(refreshToken.getExpiration().compareTo(Instant.now()) < 0) {
                refreshTokenRepository.delete(refreshToken);
                throw new AppException();
            }
            accessToken.setAccessToken(token);
        }, () -> {
            throw new AppException();
        });
        return accessToken;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {
        AtomicReference<RefreshToken> atomicReference = new AtomicReference<>();
        refreshTokenRepository.findFirstByUser(user).ifPresentOrElse(refreshToken -> {
            refreshToken.setToken(UUID.randomUUID().toString());
            refreshToken.setExpiration(Instant.now().plusMillis(expirationRefresh));
            atomicReference.set(refreshToken);
        }, () -> {
            RefreshToken refreshToken = new RefreshToken().builder()
                    .token(UUID.randomUUID().toString())
                    .expiration(Instant.now().plusMillis(expirationRefresh))
                    .user(user)
                    .build();
            atomicReference.set(refreshToken);
        });
        refreshTokenRepository.save(atomicReference.get());
        return atomicReference.get();
    }
}
