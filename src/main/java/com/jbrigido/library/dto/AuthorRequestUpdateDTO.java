package com.jbrigido.library.dto;


import java.time.LocalDate;

public record AuthorRequestUpdateDTO(String name, String lastname, LocalDate birthday, String nationality) {
}
