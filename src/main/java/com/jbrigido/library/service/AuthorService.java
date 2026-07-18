package com.jbrigido.library.service;

import com.jbrigido.library.dto.AuthorRequestDTO;
import com.jbrigido.library.dto.AuthorResponseDTO;
import com.jbrigido.library.entity.Author;
import com.jbrigido.library.mapper.AuthorMapper;
import com.jbrigido.library.repository.author.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

    public AuthorService(AuthorRepository repository, AuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AuthorResponseDTO create(AuthorRequestDTO request) {
        Author converted = mapper.toEntity(request);
        Author saved = repository.save(converted);
        return mapper.toResponse(saved);
    }

}
