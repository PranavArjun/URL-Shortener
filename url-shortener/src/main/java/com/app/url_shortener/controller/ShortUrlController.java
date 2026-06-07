package com.app.url_shortener.controller;

import com.app.url_shortener.dto.CreateShortUrlRequestDto;
import com.app.url_shortener.dto.ShortUrlResponseDto;
import com.app.url_shortener.repository.UserRepository;
import com.app.url_shortener.service.ShortUrlService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {
    private final ShortUrlService service;

    public ShortUrlController(ShortUrlService service){
        this.service=service;
    }

    @GetMapping()
    public Page<ShortUrlResponseDto> getAllPublicShortUrls(@RequestParam (defaultValue = "0") int page){
        return service.getAllPublicShortUrls(page);
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
    public Page<ShortUrlResponseDto> getLoggedInUser(
            @RequestParam(defaultValue = "0") int page
    ){
        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return service.getUserUrls(email, page);
    }
}
