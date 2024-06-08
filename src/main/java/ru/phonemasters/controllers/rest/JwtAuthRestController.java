package ru.phonemasters.controllers.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.phonemasters.dto.AuthRequestDTO;
import ru.phonemasters.dto.JwtResponseDTO;
import ru.phonemasters.services.JwtService;
import ru.phonemasters.services.UserService;

@RestController
public class JwtAuthRestController {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public JwtAuthRestController(JwtService jwtService, AuthenticationManager authenticationManager) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Authenticates the user and returns a JWT token if successful.
     *
     * @param authRequestDTO the user's authentication request
     * @return a JWT response containing the access token
     */
    @PostMapping("/api/v1/login")
    @CrossOrigin(origins = "localhost:8080")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
        if (authentication.isAuthenticated()) {
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.GenerateToken(authRequestDTO.getUsername())).build();
        } else {
            throw new UsernameNotFoundException("Invalid user request..!!");
        }
    }
}
