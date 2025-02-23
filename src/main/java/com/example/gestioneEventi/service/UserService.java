package com.example.gestioneEventi.service;

import com.example.gestioneEventi.enumeration.RoleType;
import com.example.gestioneEventi.exception.EmailDuplicateException;
import com.example.gestioneEventi.exception.RoleNotFound;
import com.example.gestioneEventi.exception.UsernameDuplicateException;
import com.example.gestioneEventi.model.Role;
import com.example.gestioneEventi.model.User;
import com.example.gestioneEventi.payload.request.RegistrationRequest;
import com.example.gestioneEventi.payload.response.UserDto;
import com.example.gestioneEventi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Scanner;
import java.util.Set;

@Service
@Transactional

public class UserService {

    Scanner sc = new Scanner(System.in);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleService roleService;

    public String userChoice;

    public String insertUser(RegistrationRequest userRequest, Set<String> roles){
        checkDuplicatedKey(userRequest.getUsername(), userRequest.getEmail());

        User user = new User(
                userRequest.getUsername(),
                userRequest.getPassword(),
                userRequest.getFirstName(),
                userRequest.getLastName(),
                userRequest.getEmail());
        userRepository.save(user);

        roles.forEach(roleName -> {
            try {
                RoleType roleType = RoleType.valueOf(roleName.toUpperCase());
                Role role = roleService.getByRoleType(roleType).orElseThrow(() -> new  RoleNotFound("Il ruolo non è stato trovato"));
                user.addRole(role);
            } catch (RoleNotFound e){
                System.out.println(e.getMessage());
            }
        });

        return "L'utente " + user.getUsername() + " con id " + user.getId() + " è stato salvato correttamente";
    }

    // ❗❗ I campi di registrazione dovranno essere convertity nell'entity di utente

    // verificare eventuali campi duplicati --> la gestione try/catch affidata al controller
    public void checkDuplicatedKey(String username, String email){

        if(userRepository.existsByUsername(username)){
            throw new UsernameDuplicateException("L'username inserito è già stato utilizzato");
        }

        if(userRepository.existsByEmail(email)){
            throw new EmailDuplicateException("L'email inserita è già stata utilizzata");
        }
    }

    // travaso DTO -> ENTITY
    public User dto_entity(UserDto userDto){
        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        return user;
    }

    // travaso ENTITY -> DTO
    public UserDto entity_dto(User user){
        UserDto userDto = new UserDto();

        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());

        return userDto;
    }

}
