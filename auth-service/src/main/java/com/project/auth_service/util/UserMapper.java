package com.project.auth_service.util;

import com.project.auth_service.dto.AuthRegisterDTO;
import com.project.auth_service.entity.User;
import com.project.auth_service.entity.enums.Role;

public final class UserMapper {

    private UserMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated!"); 
    }

    public static User toEntity(AuthRegisterDTO registerDTO, String encodedPassword) {
        return new User(registerDTO.getEmail(), registerDTO.getFullName(), encodedPassword, Role.valueOf(registerDTO.getRole().toUpperCase()));
    }
}
