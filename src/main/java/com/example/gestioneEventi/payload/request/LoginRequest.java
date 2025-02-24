package com.example.gestioneEventi.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data

public class LoginRequest {

    @NotBlank(message = "Il campo username risulta vuoto")
    @Size(min = 3, max = 15)
    private String username;

    @NotBlank(message = "Il campo password risulta vuoto")
    @Size(min = 3, max = 20)
    private String password;

}
