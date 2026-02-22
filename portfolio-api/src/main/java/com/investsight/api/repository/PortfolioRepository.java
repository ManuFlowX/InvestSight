package com.investsight.api.repository;

import com.investsight.api.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    // Spring Data JPA es tan inteligente que si nombramos el método así,
    // él hace la consulta SQL automáticamente para buscar el portafolio de un usuario específico.
    Portfolio findByUserId(Long userId);

}