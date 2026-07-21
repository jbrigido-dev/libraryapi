package com.jbrigido.library.controller;

import com.jbrigido.library.dto.AuthorRequestDTO;
import com.jbrigido.library.dto.AuthorRequestUpdateDTO;
import com.jbrigido.library.dto.AuthorResponseDTO;
import com.jbrigido.library.exception.ResourceNotFound;
import com.jbrigido.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {


    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AuthorResponseDTO>> listAll() {
        List<AuthorResponseDTO> retrieved = service.list();
        return ResponseEntity.ok(retrieved);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> getById(@PathVariable Long id) throws ResourceNotFound {
        AuthorResponseDTO response = service.listById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) throws ResourceNotFound {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AuthorResponseDTO> update(@PathVariable Long id, @RequestBody AuthorRequestUpdateDTO request) throws ResourceNotFound {
        AuthorResponseDTO response = service.update(id, request);
        return  ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/create")
    public ResponseEntity<AuthorResponseDTO> create(@Valid @RequestBody AuthorRequestDTO request) {
        AuthorResponseDTO created = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


}
