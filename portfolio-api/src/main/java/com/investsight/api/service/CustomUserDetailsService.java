package com.investsight.api.service;

import com.investsight.api.model.User;
import com.investsight.api.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Le dice a Spring que esta clase contiene lógica de negocio
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // Conectamos nuestro repositorio para poder buscar en la base de datos
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Este es el metodo que Spring Security llamará automáticamente cuando alguien intente hacer login
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 1. Buscamos al usuario en nuestra base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Traducimos nuestro 'User' al formato que Spring Security entiende ('UserDetails')
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword()) // Como le pusimos {noop} antes, Spring sabrá leerla
                .roles(user.getRole().name()) // Le pasamos el rol (ADMIN, ADVISOR o CLIENT)
                .build();
    }
}