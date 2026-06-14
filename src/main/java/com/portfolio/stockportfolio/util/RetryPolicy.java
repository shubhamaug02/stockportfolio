package com.portfolio.stockportfolio.util;

public record RetryPolicy(int maxAttempts, long baseDelayMs, long maxDelayMs) {
}
