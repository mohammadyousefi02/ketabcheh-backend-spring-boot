package com.mohammadyousefi.ketabcheh.book;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import validator.FileSize;

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
    @FileSize
    private MultipartFile file;
    @NotNull
    @FileSize(maxFileSize = 500_000)
    private MultipartFile image;
}
