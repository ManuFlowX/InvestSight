package com.investsight.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
    @NotBlank(message = "El símbolo del activo no puede estar vacío")
    @Column(nullable = false)
    private String symbol;

    // Cuántas acciones/monedas compró. Usamos BigDecimal por si son fracciones (Ej: 0.5 Bitcoin)
    @Positive(message = "La cantidad de acciones debe ser mayor a cero")
    @Column(nullable = false, precision = 15, scale = 6)
    private BigDecimal quantity;

    // El precio promedio al que las compró
    @Positive(message = "El precio debe ser mayor a cero")
    @Column(name = "average_price", nullable = false, precision = 15, scale = 2)
    private BigDecimal averagePrice;

    // Relación Muchos a Uno: Muchos activos pertenecen a UN solo portafolio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id", nullable = false)
    @com.fasterxml.jackson.annotation.JsonIgnore
    private Portfolio portfolio;

}