package com.jbrigido.library.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "book")
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 13, unique = true)
    private String isbn;

    @Column(nullable = false)
    @Min(value = 1)
    private Integer edition;

    @Column(nullable = false, length = 50)
    private String language;

    @Column(nullable = false)
    private LocalDate publishDate;

    @Column(nullable = false, length = 100)
    private String publisher;

    @OneToMany(mappedBy = "book")
    private List<BookAuthor> bookAuthors;
}
