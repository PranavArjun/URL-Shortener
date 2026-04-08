package com.app.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record CreateShortUrlRequestDto(
        @NotBlank(message = "URL cannot be empty")
        @URL(message = "Invalid URL format")
        String originalUrl
) {
}
