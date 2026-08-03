package com.jbrigido.library.controller;


import com.jbrigido.library.dto.UserRequestDTO;
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

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequestDTO request){
        service.register(request);
        return ResponseEntity.noContent().build();
    }

}
