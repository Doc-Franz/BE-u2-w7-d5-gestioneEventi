package com.example.gestioneEventi.payload.response;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data

public class EventDto {

    @NotBlank(message = "Il campo eventName risulta vuoto")
    private String eventName;

    @NotNull(message = "Il campo eventDate risulta vuoto")
    private LocalDate eventDate;
}
