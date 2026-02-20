package com.investsight.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "=== ACCESO CONCEDIDO: Bienvenido Administrador. Tienes control total. ===";
    }

    @GetMapping("/advisor")
    public String advisorEndpoint() {
        return "=== ACCESO CONCEDIDO: Bienvenido Asesor. Aquí puedes ver a tus clientes. ===";
    }

    @GetMapping("/client")
    public String clientEndpoint() {
        return "=== ACCESO CONCEDIDO: Bienvenido Cliente. Este es tu portafolio. ===";
    }
}