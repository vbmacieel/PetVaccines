package com.project.user_service.security;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.user_service.config.SecurityConfig;
import com.project.user_service.entity.User;
import com.project.user_service.repository.UserRepository;
import com.project.user_service.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final UserRepository repository;
    private final JwtUtil util;

    public UserAuthenticationFilter(UserRepository repository, JwtUtil util) {
        this.repository = repository;
        this.util = util;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (checkIfEndpointIsNotPublic(request)) {
            String token = recoverToken(request);

            if (token != null) {
                String userEmailFromToken = util.getUserEmailFromToken(token);
                Optional<User> optionalUser = repository.findByEmail(userEmailFromToken);

                if (optionalUser.isPresent()) {
                    UserDetails user = optionalUser.get();
                    UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }

        } else {
            throw new RuntimeException("Not allowed to acess this endpoint!");
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }

        return null;
    }

    private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return Arrays.asList(SecurityConfig.RESTRICTED_ENDPOINTS).contains(requestURI);
    }
}
