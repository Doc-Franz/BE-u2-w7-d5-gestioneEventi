package com.example.gestioneEventi.repository;

import com.example.gestioneEventi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // check login
    public Optional<User> findByUsername(String username);

    // check duplicated key
    public boolean existByUsername(String username);
    public boolean existByEmail(String email);
}
