package com.mohammadyousefi.ketabcheh.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotNull
    private String name;
    private Long parentCategoryId;
}
