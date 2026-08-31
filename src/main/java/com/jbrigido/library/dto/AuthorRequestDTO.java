package com.jbrigido.library.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record AuthorRequestDTO(@NotBlank @Size(max = 100) String name, @NotBlank @Size(max = 100) String lastname, @NotNull @PastOrPresent LocalDate birthday, @NotBlank @Size(max = 50) String nationality) {
}
