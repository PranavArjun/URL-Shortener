package com.app.url_shortener.scheduler;

import com.app.url_shortener.repository.ShortUrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor

public class ClickCountFlushJob {

    // needs these two
    private final RedisTemplate<String, String> redisTemplate;
    private final ShortUrlRepository shortUrlRepository;

    @Transactional
    @Scheduled(fixedDelay = 300000) // 5 mins in milliseconds
    public void flushClickCounts() {
        // 1. scan all clicks:* keys
        Set<String> keys = redisTemplate.keys("clicks:*");
        if(keys == null || keys.isEmpty()) return;

        // 2. for each key → getAndDelete
        for(String key : keys){
            String delta = redisTemplate.opsForValue().get(key);
            redisTemplate.delete(key);
            // delta is the click count as string e.g "5"
            // 3. update DB with delta key looks like "clicks:aB3kP9" we need just "aB3kP9"
            String shortKey = key.replace("clicks:", "");
            if(delta != null && Long.parseLong(delta) > 0){
                shortUrlRepository.incrementClickCount(shortKey, Long.parseLong(delta));
            }
        }


    }
}
