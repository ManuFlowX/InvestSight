package com.investsight.api.config;

import com.investsight.api.model.Asset;
import com.investsight.api.model.Portfolio;
import com.investsight.api.model.Role;
import com.investsight.api.model.User;
import com.investsight.api.repository.AssetRepository;
import com.investsight.api.repository.PortfolioRepository;
import com.investsight.api.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository,
                                      PortfolioRepository portfolioRepository,
                                      AssetRepository assetRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Solo insertamos si la base de datos está vacía
            if (userRepository.count() == 0) {

                // 1. Crear usuarios base
                User admin = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin123"))
                        .role(Role.ADMIN)
                        .build();

                User advisor = User.builder()
                        .username("advisor")
                        .password(passwordEncoder.encode("advisor123"))
                        .role(Role.ADVISOR)
                        .build();

                User client = User.builder()
                        .username("client")
                        .password(passwordEncoder.encode("client123"))
                        .role(Role.CLIENT)
                        .build();

                userRepository.saveAll(List.of(admin, advisor, client));

                // 2. Crear un Portafolio para el cliente
                Portfolio clientPortfolio = Portfolio.builder()
                        .user(client)
                        .totalValue(new BigDecimal("37500.00")) // Valor total inicial
                        .build();

                portfolioRepository.save(clientPortfolio);

                // 3. Comprar activos (Assets) y meterlos al portafolio
                Asset appleStock = Asset.builder()
                        .symbol("AAPL")
                        .quantity(new BigDecimal("50.000000")) // 50 acciones
                        .averagePrice(new BigDecimal("150.00")) // a $150 cada una
                        .portfolio(clientPortfolio)
                        .build();

                Asset bitcoin = Asset.builder()
                        .symbol("BTC")
                        .quantity(new BigDecimal("0.500000")) // Medio Bitcoin
                        .averagePrice(new BigDecimal("60000.00")) // comprado a $60k
                        .portfolio(clientPortfolio)
                        .build();

                assetRepository.saveAll(List.of(appleStock, bitcoin));

                System.out.println("=== DATOS FINANCIEROS INICIALES CARGADOS CON EXITO ===");
            }
        };
    }
}