package com.portfolio.stockportfolio.ratelimit;

import com.portfolio.stockportfolio.exception.RateLimitExceededException;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {
    private final ConcurrentHashMap<String, SlidingWindowCounter> counters = new ConcurrentHashMap<>();

    @Before("@annotation(rateLimit)")
    public void enforceRateLimit(JoinPoint joinpoint, RateLimit rateLimit){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String clientIp = request.getRemoteAddr();
        String methodKey = joinpoint.getSignature().toShortString();
        String bucketKey = clientIp + ":" + methodKey;

        SlidingWindowCounter counter = counters.computeIfAbsent(bucketKey, k -> new SlidingWindowCounter(rateLimit.windowSeconds() * 1000L, rateLimit.limit()));

        if(!counter.tryConsume()){
            throw new RateLimitExceededException("Too many requests, please try again later");
        }
    }
}
