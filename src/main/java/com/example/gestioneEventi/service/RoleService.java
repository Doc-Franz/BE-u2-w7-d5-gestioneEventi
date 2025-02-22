package com.example.gestioneEventi.service;

import com.example.gestioneEventi.model.Role;
import com.example.gestioneEventi.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void insertRole(Role role){
        roleRepository.save(role);
    }
}
