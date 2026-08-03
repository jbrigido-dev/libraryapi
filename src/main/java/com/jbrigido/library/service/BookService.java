package com.jbrigido.library.service;

import com.jbrigido.library.dto.BookAuthorRequestDTO;
import com.jbrigido.library.dto.BookRequestDTO;
import com.jbrigido.library.dto.BookRequestUpdateDTO;
import com.jbrigido.library.dto.BookResponseDTO;
import com.jbrigido.library.entity.Author;
import com.jbrigido.library.entity.Book;
import com.jbrigido.library.exception.IsbnAlreadyExistsException;
import com.jbrigido.library.exception.ListSizeException;
import com.jbrigido.library.exception.ResourceNotFoundException;
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

        existByIsbn(request.isbn());

        isEmptyListAuthors(request.authors());

        List<Author> authors = getAuthors(request.authors());

        Book book = mapper.toEntity(request);

        Book saved = repository.save(book);

        saveAuthors(authors, saved);

        return new BookResponseDTO(saved.getId(), saved.getTitle());

    }

    @Transactional
    public BookResponseDTO updateBook(BookRequestUpdateDTO request, Long id) {

        Book book = findById(id);

        if (!request.isbn().equals(book.getIsbn())) {
            existByIsbn(request.isbn());
            book.setIsbn(request.isbn());
        }

        if (request.title() != null) {
            book.setTitle(request.title());
        }
        if (request.edition() != null) {
            book.setEdition(request.edition());
        }
        if (request.isbn() != null) {
            book.setIsbn(request.isbn());
        }
        if (request.language() != null) {
            book.setLanguage(request.language());
        }
        if (request.publisher() != null) {
            book.setPublisher(request.publisher());
        }
        if (request.publishDate() != null) {
            book.setPublishDate(request.publishDate());
        }

        if (request.authors() != null) {
            isEmptyListAuthors(request.authors());

            List<Author> authors = getAuthors(request.authors());

            bookAuthorService.deleteByBook(book);

            saveAuthors(authors, book);

        }
        return new BookResponseDTO(book.getId(), book.getTitle());
    }

    private List<Author> getAuthors(List<Long> listId) {
        List<Author> authors = new ArrayList<>();
        for (Long id : listId) {
            Author author = authorService.getAuthorOrThrowAnException(id);
            authors.add(author);
        }
        return authors;
    }

    private void saveAuthors(List<Author> authors, Book book) {
        for (Author author : authors) {
            BookAuthorRequestDTO bookAuthorRequestDTO = new BookAuthorRequestDTO(book, author);
            bookAuthorService.save(bookAuthorRequestDTO);
        }
    }

    private void isEmptyListAuthors(List<Long> authors) {
        if (authors.isEmpty()) throw new ListSizeException("Author list must have at least one author");
    }

    private void existByIsbn(String isbn) {
        if (repository.existsByIsbn(isbn)) throw new IsbnAlreadyExistsException("ISBN is already registered");
    }

    private Book findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
    }

    public BookResponseDTO findIsbn(String isbn) {

        Book book = repository.findByIsbn(isbn).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return new BookResponseDTO(book.getId(), book.getTitle());
    }

    public List<BookResponseDTO> getAll() {
        List<BookResponseDTO> response = new ArrayList<>();
        List<Book> books = repository.findAll();
        for (Book book : books) {
            response.add(new BookResponseDTO(book.getId(), book.getTitle()));
        }

        return response;
    }

    public void deleteByID(Long id) {
        repository.deleteById(id);
    }

}
