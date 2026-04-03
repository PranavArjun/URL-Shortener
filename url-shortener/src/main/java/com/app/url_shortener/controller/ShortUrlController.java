package com.app.url_shortener.controller;

import com.app.url_shortener.entities.ShortUrl;
import com.app.url_shortener.service.ShortUrlService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
public class ShortUrlController {
    private final ShortUrlService service;

    public ShortUrlController(ShortUrlService service){
        this.service=service;
    }

    @GetMapping
    public List<ShortUrl> getAllPublicShortUrls(){
        return service.getAllPublicShortUrls();
    }
}
