package com.example.gestioneEventi.controller;

import com.example.gestioneEventi.exception.EventNotFound;
import com.example.gestioneEventi.exception.UserNotFound;
import com.example.gestioneEventi.model.Event;
import com.example.gestioneEventi.model.User;
import com.example.gestioneEventi.payload.response.EventDto;
import com.example.gestioneEventi.repository.EventRepository;
import com.example.gestioneEventi.repository.UserRepository;
import com.example.gestioneEventi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")

public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EventRepository eventRepository;

    @PostMapping("/new")
    public ResponseEntity<String> insertEvent(@Validated @RequestBody EventDto eventDto, BindingResult validation){

        if (validation.hasErrors()){

            StringBuilder errorMessage = new StringBuilder("Problemi nella validazione dei dati :\n");

            for (ObjectError error : validation.getAllErrors()){
                errorMessage.append(error.getDefaultMessage()).append("\n");
            }

            return new ResponseEntity<>(errorMessage.toString(), HttpStatus.BAD_REQUEST);
        }

        Event event = eventService.dto_entity(eventDto);
        eventService.insertEvent(event);

        return new ResponseEntity<>("L'evento con id " + event.getId() + " è stato salvato correttamente", HttpStatus.CREATED);

    }

    @PostMapping("/partecipation/{userId}/{eventId}")
    public ResponseEntity<String> insertPartecipation(@PathVariable Long userId,@PathVariable Long eventId){

        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFound("L'utente non è stato trovato"));
            Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFound("L'evento non è stato trovato"));

            // aggiungere l'evento all'elenco delle prenotazioni
            user.addPartecipation(event);

            userRepository.save(user);

            return new ResponseEntity<>("La partecipazione è stata aggiunta alla lista!", HttpStatus.CREATED);
        } catch (UserNotFound | EventNotFound ex) {
            return new  ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
