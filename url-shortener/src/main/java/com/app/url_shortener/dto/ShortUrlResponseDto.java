package com.app.url_shortener.dto;

import java.time.Instant;

public record ShortUrlResponseDto (Long id, String shortKey, String originalUrl,
                                  Boolean isPrivate, Instant expiresAt,
                                  UserResponseDto createdBy, Long clickCount,
                                  Instant createdAt){

}
