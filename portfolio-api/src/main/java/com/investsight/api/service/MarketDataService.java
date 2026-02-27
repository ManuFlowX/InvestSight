package com.investsight.api.service;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MarketDataService {

    /**
     * Este metodo simula ir a Wall Street a buscar el precio actual de una acción.
     * Recibe el símbolo (Ej: AAPL) y devuelve su precio actual en el mercado.
     */
    public BigDecimal getCurrentPrice(String symbol) {

        // Simulación de precios de mercado (Más adelante aquí pondremos la conexión a Yahoo Finance)
        return switch (symbol.toUpperCase()) {
            case "AAPL" -> new BigDecimal("175.50"); // Apple subió a 175.50
            case "BTC" -> new BigDecimal("65000.00"); // Bitcoin subió a 65k
            case "TSLA" -> new BigDecimal("190.25"); // Tesla bajó un poco
            case "MSFT" -> new BigDecimal("410.00"); // Microsoft subió a 410

            // Si piden una empresa que no conocemos, le damos un precio aleatorio entre $50 y $150
            default -> {
                double randomPrice = 50.0 + (Math.random() * 100);
                yield new BigDecimal(randomPrice).setScale(2, RoundingMode.HALF_UP);
            }
        };
    }
}