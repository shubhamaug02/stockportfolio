package com.portfolio.stockportfolio.ratelimit;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RedisRateLimiter {
    private final StringRedisTemplate redisTemplate;
    private final RedisScript<Long> script;

    public RedisRateLimiter(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.script = RedisScript.of(new ClassPathResource("scripts/sliding_window.lua"), Long.class);
    }

    public boolean isAllowed(String bucketKey, long limit, long windowSeconds) {
        long windowSizeMillis = windowSeconds * 1000L;
        long now = System.currentTimeMillis();
        long windowIndex = now/windowSizeMillis;

        String currentKey = "ratelimit:" + bucketKey + ":" + windowIndex;
        String previousKey = "ratelimit:" + bucketKey + ":" + (windowIndex-1);

        Long result = redisTemplate.execute(script,
                List.of(currentKey,previousKey),
                String.valueOf(limit),
                String.valueOf(windowSizeMillis),
                String.valueOf(now),
                String.valueOf(windowSeconds*2));

        return result!=null && result == 1L;
    }
    
}
