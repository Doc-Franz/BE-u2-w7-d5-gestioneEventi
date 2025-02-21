package com.example.gestioneEventi.model;

import com.example.gestioneEventi.enumeration.RoleType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType;
}
