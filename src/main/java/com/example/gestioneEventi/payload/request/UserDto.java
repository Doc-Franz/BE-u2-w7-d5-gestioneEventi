package com.example.gestioneEventi.payload.request;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
