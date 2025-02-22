package com.example.gestioneEventi.security.jwt;

import com.example.gestioneEventi.security.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private String analyzeJwt(HttpServletRequest request){

        // recupero del'header da authorization
        String headerAuthentication = request.getHeader("Authorization");

        // controllare se vi è del testo nel valore di Authorization
        // controllare se il valore inizia con "Bearer "
        if(StringUtils.hasText(headerAuthentication) && headerAuthentication.startsWith("Bearer ")){
            // recupero la sottostringa dopo "Bearer " all'indice 7
            return headerAuthentication.substring(7);
        }

        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // recupero della sottostringa dopo "Bearer " oppure null
        String jwt = analyzeJwt(request);

        // se la richiesta presenta un JWT lo convalidiamo --> richiamiamo il metodo in JwtUtils
        if (jwt != null && jwtUtils.validationJwtToken(jwt)){

            // recupero dell'username dal jwt token
            String username = jwtUtils.getUsernameFromToken(jwt);

            // recupero UserDetails da Username --> creazione di un oggetto authentication (generato in fase di login)
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // creazione di un oggetto UsernamePasswordAuthenticationToken
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());

            // settare nei dettagli dell'oggeto UsernamePasswordAuthenticationToken
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // impostare lo UserDetails corrente nel contesto di Security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }
}
