package com.mohammadyousefi.ketabcheh.category;

import lombok.Data;

@Data
public class CreateCategoryDto {
    private String name;
    private Long parentCategoryId;
}
