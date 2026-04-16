package com.app.url_shortener.controller;

import com.app.url_shortener.dto.CreateShortUrlRequestDto;
import com.app.url_shortener.dto.ShortUrlResponseDto;
import com.app.url_shortener.entities.ShortUrl;
import com.app.url_shortener.entities.User;
import com.app.url_shortener.repository.UserRepository;
import com.app.url_shortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Security;
import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {
    private final ShortUrlService service;
    private final UserRepository userRepository;

    public ShortUrlController(ShortUrlService service,UserRepository userRepository){
        this.userRepository=userRepository;
        this.service=service;
    }

    @GetMapping
    public List<ShortUrlResponseDto> getAllPublicShortUrls(){
        return service.getAllPublicShortUrls();
    }

    @PostMapping
    public ResponseEntity<ShortUrlResponseDto> createShortUrl(
            @Valid @RequestBody CreateShortUrlRequestDto request){
        return ResponseEntity.ok(service.createShortUrl(request));
    }

    @GetMapping("/{shortKey}")
    public ResponseEntity<Void> redirectToOriginalURL(@PathVariable String shortKey){
        String originalUrl = service.getOriginalUrl(shortKey);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl)).build();
    }

    @GetMapping("/my-urls")
    public List<ShortUrl> getLoggedInUser(){
        String email =  SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        return service.getUserUrls(email);
    }
}
