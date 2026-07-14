package com.jbrigido.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AuthorRequestDTO(@NotBlank String name, @NotBlank String lastname, @NotNull LocalDate birthday, @NotBlank String nationality) {
}
