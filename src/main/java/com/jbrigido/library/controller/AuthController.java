package com.jbrigido.library.controller;


import com.jbrigido.library.dto.LoginRequestDTO;
import com.jbrigido.library.dto.LoginResponseDTO;
import com.jbrigido.library.dto.UserRequestDTO;
import com.jbrigido.library.service.AuthService;
import com.jbrigido.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequestDTO request) {
        userService.register(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signIn(@RequestBody @Valid LoginRequestDTO request) {
        LoginResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);
    }

}
