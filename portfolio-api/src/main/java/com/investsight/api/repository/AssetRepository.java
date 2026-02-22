package com.investsight.api.repository;

import com.investsight.api.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {

    // Busca todas las inversiones (acciones, bonos) que pertenezcan a un portafolio específico
    List<Asset> findByPortfolioId(Long portfolioId);

}