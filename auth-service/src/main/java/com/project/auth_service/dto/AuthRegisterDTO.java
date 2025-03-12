package com.project.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRegisterDTO {

    @NotBlank(message = "Email is required!")
    @Email(message = "Email invalid. You must use a valid email! (ex: user@email.com).")
    private String email;

    @NotBlank(message = "Full name is required!")
    private String fullName;
    
    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String password;
    private String role;
}
