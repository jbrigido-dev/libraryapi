package com.jbrigido.library.mapper;

import com.jbrigido.library.dto.AuthorRequestDTO;
import com.jbrigido.library.dto.AuthorRequestUpdateDTO;
import com.jbrigido.library.dto.AuthorResponseDTO;
import com.jbrigido.library.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequestDTO request) {
        Author author = new Author();
        author.setName(request.name());
        author.setLastName(request.lastname());
        author.setBirthDate(request.birthday());
        author.setNationality(request.nationality());
        return author;
    }

    public AuthorResponseDTO toResponse(Author author) {
        return new AuthorResponseDTO(
                author.getId(),
                author.getName(),
                author.getLastName()
        );
    }
}
