package com.example.gestioneEventi.service;

import com.example.gestioneEventi.enumeration.RoleType;
import com.example.gestioneEventi.model.Role;
import com.example.gestioneEventi.repository.RoleRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional

public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    // metodo che controlla se un ruolo è già stato inserito nel database
    public boolean checkDuplicatedByRoleType(RoleType roleType){
        return roleRepository.existsByRoleType(roleType);
    }

    // metodo che riempie il database con i ruoli all'avvio dell'applicazione
    public void insertRole(RoleType roleType){
        if (!checkDuplicatedByRoleType(roleType)){
            roleRepository.save(new Role(roleType));
        }
    }

    // metodo che ricerca un ruolo tramite il nome
    public Optional<Role> getByRoleType(RoleType roleType){
        return roleRepository.findByRoleType(roleType);
    }

}
