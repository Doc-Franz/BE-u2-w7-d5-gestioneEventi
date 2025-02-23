package com.example.gestioneEventi.runner;

import com.example.gestioneEventi.enumeration.RoleType;
import com.example.gestioneEventi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component

public class Runner implements CommandLineRunner {
    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {

        roleService.insertRole(RoleType.USER);
        roleService.insertRole(RoleType.ADMIN);
        roleService.insertRole(RoleType.ORGANIZER);

    }
}
