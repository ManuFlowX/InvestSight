package com.investsight.api.controller;

import com.investsight.api.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");

        // 1. Verificamos que las credenciales sean correctas en la base de datos
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        // 2. Si pasó, cargamos al usuario y generamos su token matemático
        UserDetails user = userDetailsService.loadUserByUsername(username);
        String jwtToken = jwtService.generateToken(user);

        // 3. Se lo entregamos en un JSON limpio
        return ResponseEntity.ok(Map.of("token", jwtToken));
    }
}