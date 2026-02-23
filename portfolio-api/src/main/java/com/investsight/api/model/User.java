package com.investsight.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // Usamos "users" porque "user" es una palabra reservada en SQL
@Data // Esto de Lombok nos ahorra escribir getters y setters
@Builder // <-- ¡ESTE ES EL SUPERPODER QUE FALTABA!
@NoArgsConstructor // Necesario para que JPA y Builder no peleen
@AllArgsConstructor // Necesario para que Builder funcione
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Relación Uno a Uno: Un usuario es dueño de un portafolio
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Portfolio portfolio;
}