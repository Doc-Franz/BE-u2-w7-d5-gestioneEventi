package com.example.gestioneEventi.payload.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class JwtResponse {

    private String username;
    private long id;
    private String email;
    private List<String> rolesList;
    private String token;
    private String type = "Bearer ";

    public JwtResponse(String username, long id, String email, List<String> rolesList, String token) {
        this.username = username;
        this.id = id;
        this.email = email;
        this.rolesList = rolesList;
        this.token = token;
    }
}
