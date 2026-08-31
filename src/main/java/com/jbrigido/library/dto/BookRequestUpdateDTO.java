package com.jbrigido.library.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record BookRequestUpdateDTO(@Size(max = 200) String title, @Size(max = 13) String isbn, @Min(1) Integer edition,
                                   @Size(max = 50) String language, @PastOrPresent LocalDate publishDate, @Size(max = 100) String publisher,
                                   @Size(min = 1) List<Long> authors) {
}
