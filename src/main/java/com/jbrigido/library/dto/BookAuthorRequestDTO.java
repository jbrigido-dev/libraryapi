package com.jbrigido.library.dto;

import com.jbrigido.library.entity.Author;
import com.jbrigido.library.entity.Book;

public record BookAuthorRequestDTO(Book book, Author author) {
}
