package com.example.gestioneEventi.repository;

import com.example.gestioneEventi.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    Optional<Event> existsByEventName(String eventName);
}
