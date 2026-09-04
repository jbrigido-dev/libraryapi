package com.jbrigido.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jbrigido.library.dto.AuthorRequestDTO;
import com.jbrigido.library.dto.AuthorRequestUpdateDTO;
import com.jbrigido.library.dto.AuthorResponseDTO;
import com.jbrigido.library.exception.ResourceNotFoundException;
import com.jbrigido.library.service.AuthorService;
import com.jbrigido.library.service.JwtService;
import com.jbrigido.library.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthorService service;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserService userService;


    @Test
    void shouldReturnAuthorById() throws Exception {
        AuthorResponseDTO response = new AuthorResponseDTO(1L, "Jonathan", "Brigido");
        when(service.listById(1L)).thenReturn(response);
        mockMvc.perform(get("/authors/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jonathan"))
                .andExpect(jsonPath("$.lastname").value("Brigido"));
        verify(service).listById(1L);

    }

    @Test
    void shouldReturnAuthorNotExistsById() throws Exception {
        when(service.listById(99L)).thenThrow(new ResourceNotFoundException("Author not found"));
        mockMvc.perform(get("/authors/99")).andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Author not found"));
        verify(service).listById(99L);
    }

    @Test
    void shouldDeleteById() throws Exception {

        mockMvc.perform(delete("/authors/1")).andExpect(status().isNoContent());

        verify(service).deleteById(1L);

    }

    @Test
    void ShouldUpdateAuthor() throws Exception {
        AuthorRequestUpdateDTO request = new AuthorRequestUpdateDTO("Jonathan", "Brigido", null, null);
        AuthorResponseDTO expected = new AuthorResponseDTO(1L, "Jonathan", "Brigido");

        when(service.update(1L, request)).thenReturn(expected);
        mockMvc.perform(patch("/authors/1").contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jonathan",
                                    "lastname": "Brigido"
                                }
                                """)).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jonathan"))
                .andExpect(jsonPath("$.lastname").value("Brigido"));
        verify(service).update(1L, request);

    }

    @Test
    void shouldReturnAllAuthor() throws Exception {
        AuthorResponseDTO responseDTO1 = new AuthorResponseDTO(1L, "Jonathan", "Brigido");
        AuthorResponseDTO responseDTO2 = new AuthorResponseDTO(2L, "Joel", "Lopez");

        List<AuthorResponseDTO> all = new ArrayList<>();
        all.add(responseDTO1);
        all.add(responseDTO2);

        when(service.list()).thenReturn(all);

        mockMvc.perform(get("/authors")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Jonathan"))
                .andExpect(jsonPath("$[0].lastname").value("Brigido"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Joel"))
                .andExpect(jsonPath("$[1].lastname").value("Lopez"));

        verify(service).list();

    }

    @Test
    void shouldCreateAnAuthor() throws Exception {
        AuthorRequestDTO request = new AuthorRequestDTO("Jonathan", "Brigido", LocalDate.of(2001, 9, 28), "Mexican");
        AuthorResponseDTO response = new AuthorResponseDTO(1L, "Jonathan", "Brigido");

        when(service.create(request)).thenReturn(response);

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jonathan",
                                    "lastname": "Brigido",
                                    "birthday": "2001-09-28",
                                    "nationality": "Mexican"
                                }
                                """))
                .andExpect(status().isCreated());

        verify(service).create(request);

    }

    @Test
    void shouldReturnBadRequestWhenAuthorIsInvalid() throws Exception {

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Jonathan",
                                    "lastname": "Brigido",
                                    "nationality": "Mexican"
                                }
                                """)).andExpect(status().isBadRequest());
        verify(service, never()).create(any());
    }

}
