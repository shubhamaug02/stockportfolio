package com.portfolio.stockportfolio.util;

import com.portfolio.stockportfolio.exception.TransientException;

import java.util.function.Supplier;

public class RetryUtil {

    private static long computeDelay(int attempt, RetryPolicy policy){
        double window = Math.min(policy.maxDelayMs(), policy.baseDelayMs()*Math.pow(2,attempt));
        return (long) (Math.random()*window);
    }

    public static <T> T execute(Supplier<T> operation, RetryPolicy policy){

        int attempt=0;
        TransientException lastException = null;

        while(attempt< policy.maxAttempts()){
            try{
                return operation.get();
            }
            catch(TransientException ex){
                lastException=ex;
                attempt++;
                if(attempt==policy.maxAttempts())
                    throw ex;
                try {
                    Thread.sleep(computeDelay(attempt,policy));
                }
                catch(InterruptedException ie){
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Retry interrupted", ie);
                }
            }
        }

        throw new RuntimeException("Max retry attempts exhausted", lastException);
    }
}
