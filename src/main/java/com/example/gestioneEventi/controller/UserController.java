package com.example.gestioneEventi.controller;

import com.example.gestioneEventi.exception.EmailDuplicateException;
import com.example.gestioneEventi.exception.UsernameDuplicateException;
import com.example.gestioneEventi.payload.request.RegistrationRequest;
import com.example.gestioneEventi.payload.response.UserDto;
import com.example.gestioneEventi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/new")
    public ResponseEntity<String> insertUser(@Validated @RequestBody RegistrationRequest newUser, BindingResult validation){

        try {
            if (validation.hasErrors()) {

                String errorMessage = "Errore nella validazione: \n";

                for (ObjectError error : validation.getAllErrors()) {
                    errorMessage += error.getDefaultMessage();
                }
            }

            String insertUserMessage = userService.insertuser(newUser);
            return new ResponseEntity<>(insertUserMessage, HttpStatus.OK);
        } catch (EmailDuplicateException | UsernameDuplicateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }
}
