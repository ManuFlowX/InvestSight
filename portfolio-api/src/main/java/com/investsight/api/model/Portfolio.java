package com.investsight.api.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "portfolios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Usamos BigDecimal para manejar dinero, es mucho más preciso que un Double normal
    @Column(name = "total_value", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal totalValue = BigDecimal.ZERO;

    // Relación Uno a Uno: Este portafolio pertenece a un solo usuario
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    // Relación Uno a Muchos: Un portafolio tiene una lista de muchos activos
    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Builder.Default
    private List<Asset> assets = new ArrayList<>();
}