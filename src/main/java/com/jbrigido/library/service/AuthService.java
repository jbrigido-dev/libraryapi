package com.jbrigido.library.service;

import com.jbrigido.library.dto.LoginRequestDTO;
import com.jbrigido.library.dto.LoginResponseDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        Authentication auth = UsernamePasswordAuthenticationToken.unauthenticated(request.username(), request.password());
        Authentication authenticated = authenticationManager.authenticate(auth);
        UserDetails details = (UserDetails) authenticated.getPrincipal();
        String token = jwtService.generateToken(details);
        return new LoginResponseDTO(token);

    }
}
