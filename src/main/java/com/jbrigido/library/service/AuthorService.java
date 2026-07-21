package com.jbrigido.library.service;

import com.jbrigido.library.dto.AuthorRequestDTO;
import com.jbrigido.library.dto.AuthorRequestUpdateDTO;
import com.jbrigido.library.dto.AuthorResponseDTO;
import com.jbrigido.library.entity.Author;
import com.jbrigido.library.exception.ResourceNotFoundException;
import com.jbrigido.library.mapper.AuthorMapper;
import com.jbrigido.library.repository.author.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<AuthorResponseDTO> list() {
        List<Author> authorList = repository.findAll();
        List<AuthorResponseDTO> dtos = new ArrayList<>();

        for (Author x : authorList) {
            dtos.add(
                    mapper.toResponse(x)
            );
        }
        return dtos;
    }

    public AuthorResponseDTO listById(Long id) {
        Author author = getAuthorOrThrowAnException(id);
        return mapper.toResponse(author);
    }

    public void deleteById(Long id) {
        Author author = getAuthorOrThrowAnException(id);
        repository.delete(author);
    }

    public Author getAuthorOrThrowAnException(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
    }

    public AuthorResponseDTO update(Long id, AuthorRequestUpdateDTO request) {
        Author author =getAuthorOrThrowAnException(id);

        if (request.name() != null) {
            author.setName(request.name());
        }

        if (request.lastname() != null) {
            author.setLastName(request.lastname());
        }
        if (request.birthday() != null) {
            author.setBirthDate(request.birthday());
        }
        if (request.nationality() != null) {
            author.setNationality(request.nationality());

        }
        Author authorUpdated = repository.save(author);
        return mapper.toResponse(authorUpdated);
    }

}
