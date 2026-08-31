package com.jbrigido.library.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record BookRequestDTO(@NotBlank @Size(max = 200) String title, @Size(max = 13) String isbn  ,@NotNull @Min(value = 1) Integer edition,
                             @NotBlank @Size(max = 50) String language, @NotNull @Past LocalDate publishDate, @NotBlank @Size(max = 100) String publisher,
                             @NotNull @Size(min = 1) List<Long> authors) {
}
