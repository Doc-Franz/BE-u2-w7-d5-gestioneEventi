package com.example.gestioneEventi.repository;

import com.example.gestioneEventi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {

    // check login
    public Optional<User> findByUsername(String username);

    // check duplicated key
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
