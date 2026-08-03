package com.jbrigido.library.dto;

import com.jbrigido.library.entity.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO (@NotBlank String username, @NotBlank @Min(8) String password,@NotNull User.Role role) {
}
