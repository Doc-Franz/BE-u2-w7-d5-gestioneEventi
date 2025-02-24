package com.example.gestioneEventi.service;

import com.example.gestioneEventi.model.Event;
import com.example.gestioneEventi.payload.response.EventDto;
import com.example.gestioneEventi.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional

public class EventService {

    @Autowired
    EventRepository eventRepository;

    public String insertEvent(EventDto eventDto){
        Event event = dto_entity(eventDto);
        eventRepository.save(event);

        return "L'evento con id " + event.getId() + " è stato salvato correttamente";
    }

    // DTO -> ENTITY
    public Event dto_entity(EventDto eventDto){
        Event event = new Event();

        event.setEventName(eventDto.getEventName());
        event.setEventDate(eventDto.getEventDate());

        return event;
    }

    // ENTITY -> DTO
    public EventDto entity_dto(Event event){
        EventDto eventDto = new EventDto();

        eventDto.setEventName(event.getEventName());
        eventDto.setEventDate(event.getEventDate());

        return eventDto;
    }
}
