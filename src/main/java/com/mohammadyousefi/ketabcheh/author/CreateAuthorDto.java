package com.mohammadyousefi.ketabcheh.author;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateAuthorDto {
    @NotNull
    private String name;
}
