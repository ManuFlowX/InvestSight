package com.investsight.api.repository;

import com.investsight.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Este metodo personalizado nos dejara buscar un usuario por su nombre
    Optional<User> findByUsername(String username);
}