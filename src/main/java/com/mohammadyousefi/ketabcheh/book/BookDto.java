package com.mohammadyousefi.ketabcheh.book;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class BookDto {
    @NotNull
    private String title;
    @NotNull
    private Long publishDate;
    @NotNull
    private int stock;
    @NotNull
    private String description;
    @NotNull
    private int price;
    @NotNull
    private Long authorId;
    @NotNull
    private List<Long> categoriesIds;
    @NotNull
    @Size(max = 1000000)
    private MultipartFile file;
    @NotNull
    @Size(max = 500000)
    private MultipartFile image;
}
