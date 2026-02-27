package com.investsight.api.service;

import com.investsight.api.dto.AssetReportDTO;
import com.investsight.api.dto.PortfolioReportDTO;
import com.investsight.api.model.Asset;
import com.investsight.api.model.Portfolio;
import com.investsight.api.repository.AssetRepository;
import com.investsight.api.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    // --- Traemos al Oráculo ---
    private final MarketDataService marketDataService;

    public Portfolio getPortfolioByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    @Transactional
    public Asset addAssetToPortfolio(Long portfolioId, Asset newAsset) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portafolio no encontrado"));

        newAsset.setPortfolio(portfolio);
        Asset savedAsset = assetRepository.save(newAsset);

        BigDecimal investmentValue = newAsset.getQuantity().multiply(newAsset.getAveragePrice());
        BigDecimal newTotal = portfolio.getTotalValue().add(investmentValue);

        portfolio.setTotalValue(newTotal);
        portfolioRepository.save(portfolio);

        return savedAsset;
    }

    @Transactional
    public void removeAssetFromPortfolio(Long portfolioId, Long assetId) {
        Asset assetToRemove = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Activo no encontrado"));

        Portfolio portfolio = assetToRemove.getPortfolio();
        if (!portfolio.getId().equals(portfolioId)) {
            throw new RuntimeException("Error: Esta acción no pertenece a tu portafolio");
        }

        BigDecimal investmentValue = assetToRemove.getQuantity().multiply(assetToRemove.getAveragePrice());
        BigDecimal newTotal = portfolio.getTotalValue().subtract(investmentValue);

        portfolio.setTotalValue(newTotal);
        portfolioRepository.save(portfolio);
        assetRepository.delete(assetToRemove);
    }

    // =========================================================================
    // METODO: Generar el Reporte Financiero con Precios en Vivo (DTO)
    // =========================================================================
    public PortfolioReportDTO getPortfolioReport(Long userId) {

        Portfolio portfolio = portfolioRepository.findByUserId(userId);
        if (portfolio == null) {
            throw new RuntimeException("Portafolio no encontrado");
        }

        BigDecimal totalInvested = BigDecimal.ZERO;
        BigDecimal totalCurrentValue = BigDecimal.ZERO;
        List<AssetReportDTO> assetReports = new ArrayList<>();

        for (Asset asset : portfolio.getAssets()) {

            BigDecimal invested = asset.getQuantity().multiply(asset.getAveragePrice());
            BigDecimal currentPrice = marketDataService.getCurrentPrice(asset.getSymbol());
            BigDecimal currentValue = asset.getQuantity().multiply(currentPrice);
            BigDecimal profitOrLoss = currentValue.subtract(invested);

            totalInvested = totalInvested.add(invested);
            totalCurrentValue = totalCurrentValue.add(currentValue);

            AssetReportDTO assetDTO = AssetReportDTO.builder()
                    .symbol(asset.getSymbol())
                    .quantity(asset.getQuantity())
                    .averagePurchasePrice(asset.getAveragePrice())
                    .currentMarketPrice(currentPrice)
                    .totalCurrentValue(currentValue)
                    .profitOrLoss(profitOrLoss)
                    .build();

            assetReports.add(assetDTO);
        }

        BigDecimal totalProfitOrLoss = totalCurrentValue.subtract(totalInvested);

        return PortfolioReportDTO.builder()
                .portfolioId(portfolio.getId())
                .totalInvested(totalInvested)
                .totalCurrentValue(totalCurrentValue)
                .totalProfitOrLoss(totalProfitOrLoss)
                .assets(assetReports)
                .build();
    }
}