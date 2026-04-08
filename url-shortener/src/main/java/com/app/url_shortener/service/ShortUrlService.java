package com.app.url_shortener.service;

import com.app.url_shortener.dto.CreateShortUrlRequestDto;
import com.app.url_shortener.dto.ShortUrlResponseDto;
import com.app.url_shortener.entities.ShortUrl;
import com.app.url_shortener.repository.ShortUrlRepository;
import com.app.url_shortener.util.ShortKeyGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ShortUrlService {
    private final ShortUrlRepository repository;
    private final EntityDtoMapper entityDtoMapper;


    public ShortUrlService(ShortUrlRepository repository,EntityDtoMapper entityDtoMapper){
        this.repository=repository;
        this.entityDtoMapper=entityDtoMapper;
    }

//    Get all Public URLS
    public List<ShortUrlResponseDto> getAllPublicShortUrls() {
        return repository.getAllPublicShortUrls()
                .stream().map(url-> entityDtoMapper.toShortUrlDto(url)).toList();
    }
    public ShortUrlResponseDto createShortUrl(CreateShortUrlRequestDto request){
//        Generate unique key
        String shortKey;
        do{
            shortKey = ShortKeyGenerator.generateShortKey();
        }while(repository.existsByShortKey(shortKey));

//        Create entity
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(request.originalUrl());
        shortUrl.setShortKey(shortKey);
        shortUrl.setIsPrivate(false);
        shortUrl.setCreatedAt(Instant.now());
        shortUrl.setExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS));
        shortUrl.setCreatedBy(null);

//        Save in db
        ShortUrl saved = repository.save(shortUrl);
        System.out.println(saved);

//       Convert response to DTO
        return entityDtoMapper.toShortUrlDto(saved);
    }

    public String getOriginalUrl(String shortUrlKey){
        ShortUrl url = repository.findByShortKey(shortUrlKey).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"URL not found"));

        if(url.getExpiresAt()==null || url.getExpiresAt().isBefore(Instant.now())){
            throw new ResponseStatusException(HttpStatus.GONE,"URl expired");
        }

//        increment click count
        url.setClickCount(url.getClickCount()+1);
        repository.save(url);

        return url.getOriginalUrl();
    }
}

