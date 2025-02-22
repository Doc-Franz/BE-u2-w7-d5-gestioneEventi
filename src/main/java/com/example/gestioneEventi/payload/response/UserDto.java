package com.example.gestioneEventi.payload.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class UserDto {

    @NotBlank(message = "Il campo username risulta vuoto")
    private String username;

    @NotBlank(message = "Il campo password risulta vuoto")
    private String password;

    @NotBlank(message = "Il campo firstName risulta vuoto")
    private String firstName;

    @NotBlank(message = "Il campo lastName risulta vuoto")
    private String lastName;

    @NotBlank(message = "Il campo email risulta vuoto")
    @Email(message = "Indirizzo email non valido")
    private String email;

}
