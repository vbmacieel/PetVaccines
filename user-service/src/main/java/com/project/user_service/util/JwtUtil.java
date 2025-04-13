package com.project.user_service.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JwtUtil {

    private static final String ISSUER = "auth_service";
    private static final String CLAIM = "role";

    @Value("${jwt.secret}")
    private String secret;

    private Algorithm getSigninAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

    public String getUserEmailFromToken(String token) {
        try {
            return JWT.require(getSigninAlgorithm())
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getSubject();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error verifying the token!", exception);
        }
    }

    public String getUserRoleFromToken(String token) {
        try {
            return JWT.require(getSigninAlgorithm())
            .withIssuer(ISSUER)
            .build()
            .verify(token)
            .getClaim(CLAIM)
            .toString();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Error verifying the token!", exception);
        }
    }
}
