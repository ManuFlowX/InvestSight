package com.investsight.api.config;

import com.investsight.api.model.Role;
import com.investsight.api.model.User;
import com.investsight.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            // Verificamos si la tabla de usuarios está vacía
            if (repository.count() == 0) {

                // 1. Creamos el usuario ADMIN
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("{noop}admin123"); // {noop} le dice a Spring que por ahora no encripte la contraseña
                admin.setRole(Role.ADMIN);
                repository.save(admin); // Guardamos en la base de datos

                // 2. Creamos el usuario ADVISOR
                User advisor = new User();
                advisor.setUsername("advisor");
                advisor.setPassword("{noop}advisor123");
                advisor.setRole(Role.ADVISOR);
                repository.save(advisor);

                // 3. Creamos el usuario CLIENT
                User client = new User();
                client.setUsername("client");
                client.setPassword("{noop}client123");
                client.setRole(Role.CLIENT);
                repository.save(client);

                System.out.println("--- USUARIOS DE PRUEBA CREADOS EXITOSAMENTE ---");
            }
        };
    }
}