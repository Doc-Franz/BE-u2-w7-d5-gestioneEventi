package com.example.gestioneEventi.security.jwt;

/*
   Classe che gestisce il ciclo di vita del token
   1. creazione del token
   2. metodo che recupera username dal JWT
   3. metodo che tratta la validazione del token JWT */

import com.example.gestioneEventi.security.services.UserDetailsImpl;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component

public class JwtUtils {

    // aggiungere le costanti legate al JWT
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private String jwtExpiration;

    // Creazione JWT
    public String createJwtToken(Authentication authentication){

        // authentication è un oggetto creato da Spring Security in fase di login e contiene informazioni sull'utente -->
        // recupero i dettagli dell'utente e faccio il cast a UserDetailsImpl per poter accedere ai metodi messi a disposizione da UserDetails
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getDetails();

        // creazione del JWT

        long expirationTime = Long.parseLong(jwtExpiration);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())                            // username
                .setIssuedAt(new Date())                                            // data creazione token
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))     // data di scandenza
                .signWith(getKey(), SignatureAlgorithm.HS256)                       // firma del token con la chiave segreta
                .compact();                                                         // conversione in stringa
    }

    // recupero della scadenza del JWT
    public Date getExpirationFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token).getBody().getExpiration();
    }

    // recupero Username da JWT
    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJwt(token).getBody().getSubject();
    }

    // validazione del token JWT
    public boolean validationJwtToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(getKey()).build().parse(token);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    // recupero della chiave
    public Key getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }


}

