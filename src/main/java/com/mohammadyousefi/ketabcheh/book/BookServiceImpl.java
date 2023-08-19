package com.mohammadyousefi.ketabcheh.book;

import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findByFilter(String title, String authorName, Integer minPrice, Integer maxPrice, Pageable pageable) {
        return bookRepository.findByFilter(title, authorName, minPrice, maxPrice, pageable);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public String deleteById(Long id) {
        Optional<Book> book = findById(id);
        if (book.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("book"));
        bookRepository.deleteById(id);
        return "successfully deleted";
    }

    @Override
    public void decreaseStock(Long bookId, int quantity) {
        Optional<Book> bookOptional = findById(bookId);
        if (bookOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("book"));
        Book book = bookOptional.get();
        book.setStock(book.getStock() - quantity);
        save(book);
    }
}
