package com.example.gestioneEventi.controller;

import com.example.gestioneEventi.exception.EmailDuplicateException;
import com.example.gestioneEventi.exception.RoleNotFound;
import com.example.gestioneEventi.exception.UserNotFound;
import com.example.gestioneEventi.exception.UsernameDuplicateException;
import com.example.gestioneEventi.model.Role;
import com.example.gestioneEventi.payload.request.LoginRequest;
import com.example.gestioneEventi.payload.request.RegistrationRequest;
import com.example.gestioneEventi.payload.response.JwtResponse;
import com.example.gestioneEventi.payload.response.UserDto;
import com.example.gestioneEventi.repository.UserRepository;
import com.example.gestioneEventi.security.jwt.JwtUtils;
import com.example.gestioneEventi.security.services.UserDetailsImpl;
import com.example.gestioneEventi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")

public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/new")
    public ResponseEntity<String> insertUser(@Validated @RequestBody RegistrationRequest newUser, BindingResult validation){

        try {
            if (validation.hasErrors()) {

                String errorMessage = "Errore nella validazione: \n";

                for (ObjectError error : validation.getAllErrors()) {
                    errorMessage += error.getDefaultMessage();
                }
            }

            String insertUserMessage = userService.insertUser(newUser);
            return new ResponseEntity<>(insertUserMessage, HttpStatus.OK);
        } catch (EmailDuplicateException | UsernameDuplicateException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PatchMapping("/{username}")
    public ResponseEntity<String> updateUserRole(@PathVariable String username, @RequestParam String roleName){

        try {
            userService.updateRole(username, roleName);
            return new ResponseEntity<>("Il ruolo dell'utente è stato aggiornato correttamente", HttpStatus.OK);
        } catch (UserNotFound | RoleNotFound ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest, BindingResult validation){

        if (validation.hasErrors()){

            StringBuilder errorMessage = new StringBuilder("Problemi nella validazione dei dati :\n");

            for (ObjectError error : validation.getAllErrors()){
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        // generazione oggetto per autenticazione
        UsernamePasswordAuthenticationToken tokenNoAuth = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());

        // Invocare e recuperare l'authentication -> autenticazione va a buon fine
        // Utilizziamo il gestore delle autenticazioni che si basa su Username e Password
        // Recuperiamo l'autenticazione attraverso il metodo authenticate
        Authentication authentication = authenticationManager.authenticate(tokenNoAuth);

        // Impostare l'autenticazione nel contesto di sicurezza di Spring
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generazione del token finale
        String token = jwtUtils.createJwtToken(authentication);

        // Recuperiamo le info che vogliamo inserire nella risposta al client
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> webRoles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .toList();

        // Creare un oggetto JWT response
        JwtResponse jwtResponse = new JwtResponse(userDetails.getUsername(), userDetails.getId(), userDetails.getEmail(), webRoles, token);

        // Gestione della risposta al client
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }

}
