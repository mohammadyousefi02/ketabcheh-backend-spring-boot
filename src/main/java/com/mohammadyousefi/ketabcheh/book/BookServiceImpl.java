package com.mohammadyousefi.ketabcheh.book;

import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
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
        if(book.isEmpty()) throw new NotFoundException("there is no book with this id");
        bookRepository.deleteById(id);
        return "successfully deleted";
    }
}
