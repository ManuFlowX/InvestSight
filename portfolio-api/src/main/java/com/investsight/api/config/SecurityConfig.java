package com.investsight.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desactivamos CSRF para poder usar la consola H2 y Postman sin problemas
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        // 1. Salud de la API (Pública, cualquiera puede ver si el servidor está vivo)
                        .requestMatchers("/api/health").permitAll()

                        // 2. Base de datos H2 (Asegurada: SOLO ADMINISTRADORES) <-- ¡Tu corrección!
                        .requestMatchers("/h2-console/**").hasRole("ADMIN")

                        // 3. Rutas de prueba para verificar que los roles funcionan
                        .requestMatchers("/api/roles/admin").hasRole("ADMIN")
                        .requestMatchers("/api/roles/advisor").hasRole("ADVISOR")
                        .requestMatchers("/api/roles/client").hasRole("CLIENT")

                        // 4. Todo lo demás (cualquier otra ruta futura) requiere estar logueado
                        .anyRequest().authenticated()
                )

                // Permite que la consola H2 se muestre correctamente en el navegador
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // Activa el formulario visual de login de Spring
                .formLogin(Customizer.withDefaults())

                // Permite enviar credenciales directamente por herramientas como Postman
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}