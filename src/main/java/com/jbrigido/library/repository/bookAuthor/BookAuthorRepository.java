package com.jbrigido.library.repository.bookAuthor;

import com.jbrigido.library.entity.Book;
import com.jbrigido.library.entity.BookAuthor;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {

    void deleteByBook(Book book);
    
}