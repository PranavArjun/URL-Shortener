package com.app.url_shortener.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRegisterRequestDto (
        @NotBlank(message = "Name is required")
        String name,

        @Email(message = "Invalid email")
        String email,

        @NotBlank(message = "Password required")
        String password){}
