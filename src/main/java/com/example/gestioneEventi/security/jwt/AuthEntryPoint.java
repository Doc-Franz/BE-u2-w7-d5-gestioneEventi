package com.example.gestioneEventi.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component

// implementazione dell'interfaccia che rileva eventuali errori di autenticazione
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        // settare il formato di ritorno verso il cliente
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // settare lo status della risposta
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // contenuto di ritorno al client in caso di errore
        final Map<String, Object> infoErrors = new HashMap<>();
        infoErrors.put("stato", HttpServletResponse.SC_UNAUTHORIZED);
        infoErrors.put("errore", "Autorizzazione non valida");
        infoErrors.put("messaggio", authException.getMessage());
        infoErrors.put("path", request.getServletPath());

        // mappatura per convertire gli oggetti JAVA in formato JSON --> JSON è il formato più diffuso per API REST
        // JSON è nativamente supportato da JS, quindi le applicazioni frontend possono leggerlo senza problemi
        final ObjectMapper mappingErrors = new ObjectMapper();
        mappingErrors.writeValue(response.getOutputStream(), infoErrors);
    }
}
