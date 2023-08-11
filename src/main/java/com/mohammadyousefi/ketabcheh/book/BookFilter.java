package com.mohammadyousefi.ketabcheh.book;

import lombok.Data;

@Data
public class BookFilter {
    private String title;
    private String authorName;
    private Integer minPrice;
    private Integer maxPrice;
}
