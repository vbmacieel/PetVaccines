package com.project.auth_service.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.project.auth_service.entity.User;

@Component
public class JwtUtil {

    private static final String ISSUER = "auth_service";
    private static final String CLAIM = "claim";

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm getSigninAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String generateToken(User user) {
        try {
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withIssuedAt(getCreationDate())
                    .withExpiresAt(getExpirationDate())
                    .withSubject(user.getEmail())
                    .withClaim(CLAIM, user.getRole().name())
                    .sign(getSigninAlgorithm());
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro generating token!");
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            return JWT.require(getSigninAlgorithm())
                    .withIssuer(ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error verifying token!", exception);
        }
    }

    private Instant getCreationDate() {
        return ZonedDateTime.now().toInstant();
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
