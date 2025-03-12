package com.project.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthLoginDTO {

    @NotBlank(message = "Email is required!")
    @Email(message = "Email invalid. You must use a valid email! (ex: user@email.com).")
    private String email;

    @NotBlank(message = "Password is required.")
    private String password;
}
