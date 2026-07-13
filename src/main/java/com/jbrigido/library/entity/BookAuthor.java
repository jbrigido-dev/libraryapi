package com.jbrigido.library.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bookAuthor")
@Getter
@Setter
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(nullable = false, name = "book")
    private Book book;
    @ManyToOne
    @JoinColumn(nullable = false, name = "author")
    private Author author;

}
