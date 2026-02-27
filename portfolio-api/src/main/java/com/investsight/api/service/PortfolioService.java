package com.investsight.api.service;

import com.investsight.api.model.Asset;
import com.investsight.api.model.Portfolio;
import com.investsight.api.repository.AssetRepository;
import com.investsight.api.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public Portfolio getPortfolioByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId);
    }

    // El escudo de seguridad: Si algo falla a la mitad, se cancela toda la operación
    @Transactional
    public Asset addAssetToPortfolio(Long portfolioId, Asset newAsset) {

        // 1. Buscamos el portafolio
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new RuntimeException("Portafolio no encontrado"));

        // 2. Guardamos la nueva inversión
        newAsset.setPortfolio(portfolio);
        Asset savedAsset = assetRepository.save(newAsset);

        // --- NUEVA LÓGICA MATEMÁTICA ---
        // 3. Calculamos cuánto costó esta compra (Cantidad * Precio)
        BigDecimal investmentValue = newAsset.getQuantity().multiply(newAsset.getAveragePrice());

        // 4. Se lo sumamos al valor total que ya tenía el portafolio
        BigDecimal newTotal = portfolio.getTotalValue().add(investmentValue);

        // 5. Actualizamos y guardamos el portafolio con su nuevo saldo
        portfolio.setTotalValue(newTotal);
        portfolioRepository.save(portfolio);

        return savedAsset;
    }

    // --- NUEVO METODO: Vender (Eliminar) una inversión ---
    @Transactional
    public void removeAssetFromPortfolio(Long portfolioId, Long assetId) {

        // 1. Buscamos el activo que el cliente quiere vender usando su ID
        Asset assetToRemove = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException("Activo no encontrado"));

        // 2. Medida de seguridad: Verificamos que esta acción realmente le pertenezca
        Portfolio portfolio = assetToRemove.getPortfolio();
        if (!portfolio.getId().equals(portfolioId)) {
            throw new RuntimeException("Error: Esta acción no pertenece a tu portafolio");
        }

        // 3. Matemáticas inversas: Calculamos cuánto dinero se va a retirar
        BigDecimal investmentValue = assetToRemove.getQuantity().multiply(assetToRemove.getAveragePrice());

        // 4. Se lo RESTAMOS al valor total del portafolio (.subtract)
        BigDecimal newTotal = portfolio.getTotalValue().subtract(investmentValue);

        // 5. Actualizamos el saldo en el portafolio
        portfolio.setTotalValue(newTotal);
        portfolioRepository.save(portfolio);

        // 6. Finalmente, el cajero borra la acción de la base de datos
        assetRepository.delete(assetToRemove);
    }
}