package com.investsight.api.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users") // Usamos "users" porque "user" es una palabra reservada en SQL
@Data // Esto de Lombok nos ahorra escribir getters y setters
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
}