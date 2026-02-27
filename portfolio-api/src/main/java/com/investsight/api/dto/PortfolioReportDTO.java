package com.investsight.api.dto;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class PortfolioReportDTO {
    private Long portfolioId;

    // Todo el dinero que el cliente ha invertido
    private BigDecimal totalInvested;

    // Lo que vale su cuenta entera el día de hoy
    private BigDecimal totalCurrentValue;

    // La ganancia o pérdida total de toda su cuenta
    private BigDecimal totalProfitOrLoss;

    // La lista de sus acciones, pero servidas en el "plato bonito"
    private List<AssetReportDTO> assets;
}