package com.jbrigido.library.dto;

import java.time.LocalDate;
import java.util.List;

public record BookRequestUpdateDTO(String title, String isbn, Integer edition,
                                   String language, LocalDate publishDate, String publisher,
                                   List<Long> authors) {
}
