package com.jbrigido.library.controller;


import com.jbrigido.library.dto.BookRequestDTO;
import com.jbrigido.library.dto.BookRequestUpdateDTO;
import com.jbrigido.library.dto.BookResponseDTO;
import com.jbrigido.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO requestDTO) {
        BookResponseDTO response = service.createBook(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> listBook() {
        List<BookResponseDTO> response = service.getAll();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponseDTO> getByIsbn(@PathVariable String isbn){
        BookResponseDTO response =  service.findIsbn(isbn);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long id,@Valid @RequestBody BookRequestUpdateDTO request) {
        BookResponseDTO response = service.updateBook(request, id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponseDTO> deleteBook(@PathVariable Long id) {
        service.deleteByID(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
