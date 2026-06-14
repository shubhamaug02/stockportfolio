package com.portfolio.stockportfolio.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.stockportfolio.exception.ApiException;
import com.portfolio.stockportfolio.util.RetryPolicy;
import com.portfolio.stockportfolio.util.RetryUtil;
import com.portfolio.stockportfolio.exception.TransientException;
import com.portfolio.stockportfolio.util.StockQuoteResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import java.net.URI;
import java.net.http.HttpResponse;

@Service
public class StockService {

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${finnhub.base.url}")
    private String baseUrl;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StockQuoteResponse fetchQuote(String symbol){
        RetryPolicy policy = new RetryPolicy(3,500L, 5000L);

        return RetryUtil.execute(() -> {
            try {
                String url = baseUrl+"/quote?symbol=" + symbol + "&token=" + apiKey;

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url)).GET().build();
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if(response.statusCode()>=500){
                    throw new TransientException("Finnhub Server error: " + response.statusCode());
                }
                if(response.statusCode()>=400){
                    throw new ApiException("Bad Request to Finnhub: " + response.statusCode()) {};
                }

                JsonNode jsonNode = objectMapper.readTree(response.body());
                return new StockQuoteResponse(jsonNode.get("c").asDouble(), jsonNode.get("d").asDouble(), jsonNode.get("dp").asDouble());
            }
            catch(IOException ex){
                throw new TransientException("Network error calling Finnhub ", ex);
            }
            catch(InterruptedException ex){
                Thread.currentThread().interrupt();
                throw new TransientException("Request Interrupted ", ex);
            }
        }, policy);
    }
}
