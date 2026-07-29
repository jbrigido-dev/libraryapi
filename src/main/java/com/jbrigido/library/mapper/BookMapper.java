package com.jbrigido.library.mapper;

import com.jbrigido.library.dto.BookRequestDTO;
import com.jbrigido.library.dto.BookResponseDTO;
import com.jbrigido.library.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public Book toEntity(BookRequestDTO request) {
        Book entity = new Book();
        entity.setTitle(request.title());
        entity.setIsbn(request.isbn());
        entity.setEdition(request.edition());
        entity.setLanguage(request.language());
        entity.setPublishDate(request.publishDate());
        entity.setPublisher(request.publisher());
        return entity;
    }

    public BookResponseDTO toResponse(Book book) {
        return new BookResponseDTO(
                book.getId(),
                book.getTitle()
        );
    }
}
