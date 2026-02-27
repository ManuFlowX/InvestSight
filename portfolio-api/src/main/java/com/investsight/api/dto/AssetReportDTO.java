package com.investsight.api.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Builder
public class AssetReportDTO {
    private String symbol;
    private BigDecimal quantity;

    // Lo que el cliente pagó en el pasado
    private BigDecimal averagePurchasePrice;

    // Lo que el Oráculo dice que vale hoy
    private BigDecimal currentMarketPrice;

    // El valor actual real (cantidad * precio de hoy)
    private BigDecimal totalCurrentValue;

    // ¿Está ganando o perdiendo dinero? (Verde o Rojo)
    private BigDecimal profitOrLoss;
}