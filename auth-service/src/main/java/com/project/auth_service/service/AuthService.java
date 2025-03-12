package com.project.auth_service.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.auth_service.dto.AuthLoginDTO;
import com.project.auth_service.dto.AuthRegisterDTO;
import com.project.auth_service.dto.AuthTokenDTO;
import com.project.auth_service.entity.User;
import com.project.auth_service.exception.EmailAlreadyExistsException;
import com.project.auth_service.exception.InvalidPasswordException;
import com.project.auth_service.repository.UserRepository;
import com.project.auth_service.util.JwtUtil;
import com.project.auth_service.util.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthTokenDTO login(AuthLoginDTO loginDTO) {
        User user = repository.findByEmail(loginDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginDTO.getEmail()));

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password!");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthTokenDTO(loginDTO.getEmail(), token);
    }

    public void register(AuthRegisterDTO registerDTO) {
        Optional<User> optionalUser = repository.findByEmail(registerDTO.getEmail());

        if (optionalUser.isPresent()) {
            throw new EmailAlreadyExistsException("There is already an user with this email!");
        }

        User newUser = UserMapper.toEntity(registerDTO, passwordEncoder.encode(registerDTO.getPassword()));
        repository.save(newUser);
    }
}
