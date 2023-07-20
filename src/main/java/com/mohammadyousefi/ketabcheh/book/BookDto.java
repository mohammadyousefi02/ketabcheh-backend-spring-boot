package com.mohammadyousefi.ketabcheh.book;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BookDto {
    private String title;
    private Long publishDate;
    private int stock;
    private String description;
    private int price;
    private Long authorId;
    private List<Long> categoriesIds;
    private MultipartFile file;
    private MultipartFile image;
}
