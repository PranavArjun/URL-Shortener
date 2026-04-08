package com.app.url_shortener.service;

import com.app.url_shortener.dto.ShortUrlResponseDto;
import com.app.url_shortener.dto.UserResponseDto;
import com.app.url_shortener.entities.ShortUrl;
import com.app.url_shortener.entities.User;
import org.springframework.stereotype.Component;

@Component
public class EntityDtoMapper {
    public ShortUrlResponseDto toShortUrlDto(ShortUrl shortUrl) {
        UserResponseDto userDto = null;
        if(shortUrl.getCreatedBy() != null) {
            userDto = toUserDto(shortUrl.getCreatedBy());
        }

        return new ShortUrlResponseDto(
                shortUrl.getId(),
                shortUrl.getShortKey(),
                shortUrl.getOriginalUrl(),
                shortUrl.getIsPrivate(),
                shortUrl.getExpiresAt(),
                userDto,
                shortUrl.getClickCount(),
                shortUrl.getCreatedAt()
        );
    }

    public UserResponseDto toUserDto(User user) {
        return new UserResponseDto(user.getId(), user.getName());
    }
}
