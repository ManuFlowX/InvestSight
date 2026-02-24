package com.investsight.api.controller;

import com.investsight.api.model.Asset;
import com.investsight.api.model.Portfolio;
import com.investsight.api.service.PortfolioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/portfolios")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/{userId}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable Long userId) {
        Portfolio portfolio = portfolioService.getPortfolioByUserId(userId);

        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(portfolio);
    }

    // --- NUEVO ENDPOINT: Recibir una nueva inversión (POST) ---
    @PostMapping("/{portfolioId}/assets")
    public ResponseEntity<Asset> addAsset(
            @PathVariable Long portfolioId,
            @Valid @RequestBody Asset newAsset) {

        // Le pasamos la orden al servicio (el cerebro) para que la guarde
        Asset savedAsset = portfolioService.addAssetToPortfolio(portfolioId, newAsset);

        // Respondemos con un código 201 (CREATED) y el recibo de la inversión guardada
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAsset);
    }

    // --- NUEVO ENDPOINT: Vender/Eliminar una inversión (DELETE) ---
    @DeleteMapping("/{portfolioId}/assets/{assetId}")
    public ResponseEntity<Void> removeAsset(
            @PathVariable Long portfolioId,
            @PathVariable Long assetId) {

        // Pasamos la orden al servicio para que haga las matemáticas y borre el activo
        portfolioService.removeAssetFromPortfolio(portfolioId, assetId);

        // Respondemos con un código 204 (NO CONTENT)
        // Significa: "Operación exitosa, el archivo se borró y ya no hay nada que mostrar"
        return ResponseEntity.noContent().build();
    }
}