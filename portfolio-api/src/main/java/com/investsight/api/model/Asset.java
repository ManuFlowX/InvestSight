package com.investsight.api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // El símbolo de la inversión (Ej: "AAPL", "BTC", "TSLA")
    @Column(nullable = false)
    private String symbol;

    // Cuántas acciones/monedas compró. Usamos BigDecimal por si son fracciones (Ej: 0.5 Bitcoin)
    @Column(nullable = false, precision = 15, scale = 6)
    private BigDecimal quantity;

    // El precio promedio al que las compró
    @Column(name = "average_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal averagePrice;

    // Relación Muchos a Uno: Muchos activos pertenecen a UN solo portafolio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}