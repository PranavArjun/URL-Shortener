package com.app.url_shortener.service;

import com.app.url_shortener.dto.CreateShortUrlRequestDto;
import com.app.url_shortener.dto.ShortUrlResponseDto;
import com.app.url_shortener.entities.ShortUrl;
import com.app.url_shortener.entities.User;
import com.app.url_shortener.repository.ShortUrlRepository;
import com.app.url_shortener.repository.UserRepository;
import com.app.url_shortener.util.ShortKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;
    private final EntityDtoMapper entityDtoMapper;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;




//    Get all Public URLS
    public Page<ShortUrlResponseDto> getAllPublicShortUrls(int page) {

        Pageable pageable = PageRequest.of(page,10, Sort.by("createdAt").descending());
        return shortUrlRepository.getAllPublicShortUrls(pageable)
                .map(url->{
                    String pendingClickCount = redisTemplate.opsForValue().get("clicks:"+url.getShortKey());
                    Long pendingClicks = pendingClickCount != null ? Long.parseLong(pendingClickCount) : 0L;
                    Long totalClickCount = pendingClicks + url.getClickCount();
                    return entityDtoMapper.toShortUrlDto(url,totalClickCount);
                });
    }

    @Transactional
    public ShortUrlResponseDto createShortUrl(CreateShortUrlRequestDto request){
//        Generate unique key
        String shortKey;
        do{
            shortKey = ShortKeyGenerator.generateShortKey();
        }while(shortUrlRepository.existsByShortKey(shortKey));

//        Create entity
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(request.originalUrl());
        shortUrl.setShortKey(shortKey);
        shortUrl.setIsPrivate(false);
        shortUrl.setCreatedAt(Instant.now());
        shortUrl.setExpiresAt(Instant.now().plus(1, ChronoUnit.DAYS));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth!=null && auth.isAuthenticated()){
            User user = userRepository.findByEmail(auth.getName()).orElse(null);
            shortUrl.setCreatedBy(user);
        }
        else{
            shortUrl.setCreatedBy(null);
        }


//        Save in db
        ShortUrl saved = shortUrlRepository.save(shortUrl);
        System.out.println(saved);

        // Cache the new URL immediately after creation
        long secondsLeft = saved.getExpiresAt().getEpochSecond() - Instant.now().getEpochSecond();
        redisTemplate.opsForValue().set("url:" + saved.getShortKey(), saved.getOriginalUrl(), secondsLeft, TimeUnit.SECONDS);

//       Convert response to DTO
        return entityDtoMapper.toShortUrlDto(saved);
    }

    public String getOriginalUrl(String shortUrlKey){
//        Checking if data present in Redis
        String urlKey = "url:" + shortUrlKey;
        String cachedUrl = redisTemplate.opsForValue().get(urlKey);

//        If URL available in cache increment click count in cache only no db calls
        if(cachedUrl != null){
            redisTemplate.opsForValue().increment("clicks:" + shortUrlKey);
            return cachedUrl;
        }

        ShortUrl url = shortUrlRepository.findByShortKey(shortUrlKey).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"URL not found"));
        Instant now = Instant.now();
//      For not to have race conditions because many instant now
        if(url.getExpiresAt()==null || url.getExpiresAt().isBefore(now)){
            throw new ResponseStatusException(HttpStatus.GONE,"URl expired");
        }

        long secondsLeft = url.getExpiresAt().getEpochSecond() - now.getEpochSecond();

//        In redis set works as key, value, timeout , timeUnit
        redisTemplate.opsForValue().set(urlKey, url.getOriginalUrl(), secondsLeft, TimeUnit.SECONDS);

//        increment click count in cache nt in db
        redisTemplate.opsForValue().increment("clicks:" + shortUrlKey);

        return url.getOriginalUrl();
    }

    public Page<ShortUrlResponseDto> getUserUrls(String email,int page){
        Pageable pageable = PageRequest.of(page,10,Sort.by("createdAt").descending());
        return shortUrlRepository.findByCreatedByEmail(email,pageable)
                .map(url->{
                    String pendingClickCount = redisTemplate.opsForValue().get("clicks:"+url.getShortKey());
                    Long pendingClicks = pendingClickCount != null ? Long.parseLong(pendingClickCount) : 0L;
                    Long totalClickCount = pendingClicks + url.getClickCount();
                    return entityDtoMapper.toShortUrlDto(url,totalClickCount);
                });
    }
}

