package com.jbrigido.library.service;

import com.jbrigido.library.dto.AuthorRequestDTO;
import com.jbrigido.library.dto.AuthorRequestUpdateDTO;
import com.jbrigido.library.dto.AuthorResponseDTO;
import com.jbrigido.library.entity.Author;
import com.jbrigido.library.exception.ResourceNotFoundException;
import com.jbrigido.library.mapper.AuthorMapper;
import com.jbrigido.library.repository.author.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    AuthorMapper mapper;
    @Mock
    AuthorRepository repository;
    @InjectMocks
    AuthorService service;

    @Test
    void shouldReturnAuthorWhenIdExists() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Gabriel");

        AuthorResponseDTO expected = new AuthorResponseDTO(1L, "Gabriel", "García Márquez");
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(mapper.toResponse(author)).thenReturn(expected);
        AuthorResponseDTO response = service.listById(1L);
        assertEquals(response, expected);
        verify(repository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenAuthorDoesNotExist() {

        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.listById(1L));
    }

    @Test
    void shouldDeleteAuthorWhenIdExists() {
        Author author = new Author();
        author.setId(1L);
        author.setName("Gabriel");

        when(repository.findById(1L)).thenReturn(Optional.of(author));
        service.deleteById(1L);

        verify(repository).delete(author);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistingAuthor() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.deleteById(1L));
        verify(repository, never()).delete(any());
    }

    @Test
    void shouldCreateAuthor() {

        AuthorRequestDTO request = new AuthorRequestDTO("Gabriel", "García Márquez", null, "Mexican");

        Author author = new Author();
        author.setId(1L);
        author.setName("Gabriel");
        author.setLastName("García Márquez");
        author.setNationality("Mexican");

        Author saved = new Author();
        saved.setId(1L);
        saved.setName("Gabriel");
        saved.setLastName("García Lopez");
        saved.setNationality("Mexican");

        AuthorResponseDTO expected = new AuthorResponseDTO(1L, "Gabriel", "García Márquez");

        when(mapper.toEntity(request)).thenReturn(author);
        when(repository.save(author)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(expected);

        AuthorResponseDTO response = service.create(request);

        assertEquals(expected, response);
        verify(repository).save(author);
    }


    @Test
    void shouldUpdateAuthor() {

        AuthorRequestUpdateDTO request = new AuthorRequestUpdateDTO("Gabo", null, null, null);

        Author author = new Author();
        author.setId(1L);
        author.setName("Gabriel");
        author.setLastName("García Márquez");
        author.setNationality("Mexican");

        AuthorResponseDTO expected = new AuthorResponseDTO(1L, "Gabriel", null);

        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(repository.save(author)).thenReturn(author);
        when(mapper.toResponse(author)).thenReturn(expected);

        AuthorResponseDTO response = service.update(1L, request);

        assertEquals("Gabo", author.getName());
        assertEquals("García Márquez", author.getLastName());
        assertEquals("Mexican", author.getNationality());
        assertEquals(expected, response);

    }

    @Test
    void shouldReturnAllAuthors() {

        Author author = new Author();
        author.setId(1L);
        author.setName("Gabriel");
        author.setLastName("García Márquez");
        author.setNationality("Mexican");


        Author author1 = new Author();
        author1.setId(2L);
        author1.setName("Joel");
        author1.setLastName("Vazquez");
        author1.setNationality("Italian");

        AuthorResponseDTO r1 = new AuthorResponseDTO(1L, "Gabriel", "García Márquez");
        AuthorResponseDTO r2 = new AuthorResponseDTO(2L, "Joel", "Vazquez");

        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author1);

        List<AuthorResponseDTO> expected = new ArrayList<>();
        expected.add(r1);
        expected.add(r2);

        when(repository.findAll()).thenReturn(authors);
        when(mapper.toResponse(author)).thenReturn(r1);
        when(mapper.toResponse(author1)).thenReturn(r2);

        List<AuthorResponseDTO> list = service.list();

        assertEquals(expected, list);
        verify(repository).findAll();
    }

}
