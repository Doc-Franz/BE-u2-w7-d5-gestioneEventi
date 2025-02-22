package com.example.gestioneEventi.security.services;

import com.example.gestioneEventi.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor

public class UserDetailsImpl implements UserDetails {

    // definisco le info che andranno inserite all'interno del token JWT

    private long id;
    private String username;
    private String email;

    // la password verrà ignorata quando verrà generato il token
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> roles;

    public static UserDetailsImpl buildDetails(User user){

        // ad ogni utente è associato Set<Ruolo> --> Set<Ruolo> va convertito in List<GrantedAuthority>
        // l'interfaccia GrantedAuthority permette di convertire il nome del ruolo in un'istanza di SimpleGrantedAuthority, che Spring Security userà per gestire le autorizzazioni
        // un ruole per essere riconosciuto da Spring Security deve essere del tipo GrantedAuthority
        List<GrantedAuthority> userRoles = user.getRoleSet().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleType()
                        .name())).collect(Collectors.toList());

        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), userRoles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return "";
    }

    @Override
    public String getUsername() {
        return "";
    }
}
