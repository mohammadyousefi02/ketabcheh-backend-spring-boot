package com.mohammadyousefi.ketabcheh.book;

import com.mohammadyousefi.ketabcheh.auth.Admin;
import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.author.Author;
import com.mohammadyousefi.ketabcheh.author.AuthorService;
import com.mohammadyousefi.ketabcheh.category.Category;
import com.mohammadyousefi.ketabcheh.category.CategoryService;
import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.image.Image;
import com.mohammadyousefi.ketabcheh.response.Response;
import com.mohammadyousefi.ketabcheh.util.Upload;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookController(BookServiceImpl bookService, AuthorService authorService, CategoryService categoryService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public Response<List<Book>> findAll() {
        return new Response<>(bookService.findAll());
    }

    @GetMapping("/{id}")
    public Response<Book> findById(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isEmpty()) throw new NotFoundException("there is no book with this id");
        return new Response<>(book.get());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Authorization
    @Admin
    public Response<Book> create(@ModelAttribute BookDto bookDto) {
        Book book = new Book();
        Optional<Author> author = authorService.findById(bookDto.getAuthorId());
        if (author.isEmpty()) throw new NotFoundException("there is no author with this id");
        book.setAuthor(author.get());

        List<Category> categories = new ArrayList<>();
        for (Long categoryId : bookDto.getCategoriesIds()) {
            Category category = categoryService.findById(categoryId);
            categories.add(category);
        }
        book.setCategories(categories);
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        book.setPrice(bookDto.getPrice());
        book.setPublishDate(bookDto.getPublishDate());
        book.setStock(bookDto.getStock());
        if (bookDto.getImage().isEmpty()) throw new BadRequestException("you should upload image file");
        List<Image> images = new ArrayList<>();
        Image image = new Image();
        image.setFilename(Upload.uploadHandler(bookDto.getImage(), "thumbnails"));
        image.setBook(book);
        images.add(image);
        book.setImages(images);
        if (bookDto.getFile().isEmpty()) throw new BadRequestException("you should upload pdf file");
        String fileName = Upload.uploadHandler(bookDto.getFile(), "books");
        book.setFilename(fileName);
        book.setType(bookDto.getFile().getContentType());
        book.setSize(bookDto.getFile().getSize() + "bytes");
        return new Response<>(bookService.save(book), HttpStatus.CREATED.value());
    }

    @DeleteMapping("/{id}")
    @Authorization
    @Admin
    public Response<String> deleteById(@PathVariable Long id) {
        return new Response<>(bookService.deleteById(id));
    }
}
