package com.example.gestioneEventi.service;

import com.example.gestioneEventi.model.User;
import com.example.gestioneEventi.payload.request.UserDto;
import com.example.gestioneEventi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class UserService {

    @Autowired
    UserRepository userRepository;

    public String insertuser(User user){
        userRepository.save(user);
        return "L'utente " + user.getUsername() + " con id " + user.getId() + " è stato salvato correttamente";
    }

    // ❗❗ I campi di registrazione dovranno essere convertity nell'entity di utente

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
