package com.portfolio.stockportfolio.ratelimit;

public class SlidingWindowCounter {

    private final long windowSizeMillis;
    private final long limit;

    private long currentWindowIndex;
    private int currentWindowCount;
    private int previousWindowCount;

    public SlidingWindowCounter(long windowSizeMillis, long limit){
        this.windowSizeMillis = windowSizeMillis;
        this.limit = limit;
        this.currentWindowIndex= System.currentTimeMillis()/windowSizeMillis;
    }

    public synchronized boolean tryConsume(){
        long now = System.currentTimeMillis();
        long windowIndex = now/windowSizeMillis;

        if(windowIndex == currentWindowIndex+1){
            previousWindowCount=currentWindowCount;
            currentWindowCount=0;
            currentWindowIndex = windowIndex;
        }
        else if(windowIndex>currentWindowIndex+1){
            previousWindowCount=0;
            currentWindowCount=0;
            currentWindowIndex=windowIndex;
        }

        long elapsedInCurrentWindow = now%windowSizeMillis;
        double weight = (double) (windowSizeMillis - elapsedInCurrentWindow)/windowSizeMillis;
        double estimatedCount = previousWindowCount * weight + currentWindowCount;

        if(estimatedCount<limit){
            currentWindowCount++;
            return true;
        }
        return false;
    }

}
