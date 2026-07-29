package com.jbrigido.library.controller;


import com.jbrigido.library.dto.BookRequestDTO;
import com.jbrigido.library.dto.BookResponseDTO;
import com.jbrigido.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        BookResponseDTO response = service.createBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
