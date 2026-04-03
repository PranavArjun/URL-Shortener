package com.app.url_shortener.service;

import com.app.url_shortener.entities.ShortUrl;
import com.app.url_shortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortUrlService {
    private final ShortUrlRepository repository;

    public ShortUrlService(ShortUrlRepository repository){
        this.repository=repository;
    }

//    Get all Public URLS
    public List<ShortUrl> getAllPublicShortUrls(){
        return repository.findByIsPrivateFalseOrderByCreatedAtDesc();
    }

}

