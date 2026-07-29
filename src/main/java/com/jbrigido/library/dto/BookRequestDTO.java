package com.jbrigido.library.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record BookRequestDTO(@NotBlank String title, @NotBlank String isbn  , @Min(1) Integer edition,
                             @NotBlank String language, @NotNull LocalDate publishDate, @NotBlank String publisher,
                             @NotNull @Size(min = 1) List<Long> authors) {
}
