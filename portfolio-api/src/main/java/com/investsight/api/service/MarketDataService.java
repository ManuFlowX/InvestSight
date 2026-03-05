package com.investsight.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MarketDataService {

    private final RestTemplate restTemplate;

    @Value("${market.api.key}")
    private String apiKey;

    public BigDecimal getCurrentPrice(String symbol) {
        String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;

        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response != null && response.containsKey("Global Quote")) {
                @SuppressWarnings("unchecked")
                Map<String, String> quote = (Map<String, String>) response.get("Global Quote");
                String priceStr = quote.get("05. price");

                if (priceStr != null) {
                    return new BigDecimal(priceStr);
                }
            }
            // Si la API falla por límite de peticiones o símbolo no encontrado con la llave demo
            return new BigDecimal("150.00");
        } catch (Exception e) {
            System.err.println("Error al obtener precio para " + symbol + ": " + e.getMessage());
            return new BigDecimal("150.00");
        }
    }
}