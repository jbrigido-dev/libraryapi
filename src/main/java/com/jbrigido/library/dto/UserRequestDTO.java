package com.jbrigido.library.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO (@NotBlank @Size(max = 50) String username, @NotBlank @Size(min = 8) String password) {
}
