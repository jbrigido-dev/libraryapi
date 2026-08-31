package com.jbrigido.library.dto;


import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AuthorRequestUpdateDTO(@Size(max = 100) String name,@Size(max = 100) String lastname, @Past LocalDate birthday,@Size(max = 50) String nationality) {
}
