package com.example.gestioneEventi.security.services;

import com.example.gestioneEventi.model.User;
import com.example.gestioneEventi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // ricerca delll'utente tramite username
        Optional<User> user = userRepository.findByUsername(username);

        // istanziamento dell'utente se esiste, altrimenti viene generata un'eccezione
        User user1 = user.orElseThrow(() -> new RuntimeException("Utente non trovato"));

        // restituzione di un oggetto UserDetails con le info dell'utente da voler includere nel token
        return UserDetailsImpl.buildDetails(user1);
    }
}
