package com.jbrigido.library.service;

import com.jbrigido.library.dto.BookAuthorRequestDTO;
import com.jbrigido.library.dto.BookRequestDTO;
import com.jbrigido.library.dto.BookResponseDTO;
import com.jbrigido.library.entity.Author;
import com.jbrigido.library.entity.Book;
import com.jbrigido.library.exception.IsbnAlreadyExistsException;
import com.jbrigido.library.exception.ListSizeException;
import com.jbrigido.library.mapper.BookMapper;
import com.jbrigido.library.repository.book.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final AuthorService authorService;
    private final BookAuthorService bookAuthorService;

    public BookService(BookRepository repository, BookMapper mapper, AuthorService authorService, BookAuthorService bookAuthorService) {
        this.repository = repository;
        this.mapper = mapper;
        this.authorService = authorService;
        this.bookAuthorService = bookAuthorService;
    }

    @Transactional
    public BookResponseDTO createBook(BookRequestDTO request) {
        if (existByIsbn(request.isbn())) {
            throw new IsbnAlreadyExistsException("ISBN is already registered");
        }

        if (request.authors().isEmpty()) {
            throw new ListSizeException("Author list must have at least one author");
        }

        List<Author> authors = getAuthors(request.authors());

        Book book = mapper.toEntity(request);

        Book saved = repository.save(book);

        for (Author author : authors) {
            BookAuthorRequestDTO bookAuthorRequestDTO = new BookAuthorRequestDTO(saved, author);
            bookAuthorService.save(bookAuthorRequestDTO);
        }

        return new BookResponseDTO(saved.getId(), saved.getTitle());

    }

    private List<Author> getAuthors(List<Long> listId) {
        List<Author> authors = new ArrayList<>();
        for (Long id : listId) {
            Author author = authorService.getAuthorOrThrowAnException(id);
            authors.add(author);
        }
        return authors;
    }


    public boolean existByIsbn(String isbn) {
        return repository.existsByIsbn(isbn);
    }


}
