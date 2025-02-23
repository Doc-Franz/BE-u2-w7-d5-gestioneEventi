package com.example.gestioneEventi.repository;

import com.example.gestioneEventi.enumeration.RoleType;
import com.example.gestioneEventi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleType(RoleType roleType);
    boolean existsByRoleType(RoleType roleType);
}
