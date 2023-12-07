package com.ale.blog.security;

import com.ale.blog.entity.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
public class JwtTokenProvider implements TokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private Long EXPIRATION;

    @Override
    public String generateToken(User user, Long expiration) {
        Instant instant = Instant.now();
        Instant expiryDate = instant.plusMillis(expiration);
        return Jwts.builder()
                .setSubject(user.getUuid().toString())
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public String generateToken(User user) {
        Instant instant = Instant.now();
        Instant expiryDate = instant.plusMillis(EXPIRATION);
        return Jwts.builder()
                .setSubject(user.getUuid().toString())
                .setIssuedAt(Date.from(instant))
                .setExpiration(Date.from(expiryDate))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    @Override
    public String generateToken(UserAccess userAccess) {
        return generateToken(userAccess.getCurrentUser());
    }

    @Override
    public Optional<String> getUserId(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
            return Optional.of(claims.getSubject());
        }catch (SignatureException | MalformedJwtException ex) {
            log.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
        }
        return Optional.empty();
    }
}
