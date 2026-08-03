package com.jbrigido.library.service;

import com.jbrigido.library.dto.BookAuthorRequestDTO;
import com.jbrigido.library.dto.BookAuthorResponseDTO;
import com.jbrigido.library.entity.Book;
import com.jbrigido.library.entity.BookAuthor;
import com.jbrigido.library.mapper.BookAuthorMapper;
import com.jbrigido.library.repository.bookAuthor.BookAuthorRepository;
import org.springframework.stereotype.Service;

@Service
public class BookAuthorService {

    private final BookAuthorRepository repository;
    private final BookAuthorMapper mapper;

    public BookAuthorService(BookAuthorRepository repository, BookAuthorMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public BookAuthorResponseDTO save(BookAuthorRequestDTO request) {
        BookAuthor response = repository.save(mapper.toEntity(request));
        return new BookAuthorResponseDTO(response.getBook().getId(), response.getAuthor().getId());
    }

    public void deleteByBook(Book book){
        repository.deleteByBook(book);
    }
}
