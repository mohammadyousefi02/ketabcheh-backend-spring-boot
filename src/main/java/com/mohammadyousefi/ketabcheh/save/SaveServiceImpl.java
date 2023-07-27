package com.mohammadyousefi.ketabcheh.save;

import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.book.BookService;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.user.User;
import com.mohammadyousefi.ketabcheh.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class SaveServiceImpl implements SaveService {
    private final SaveRepository saveRepository;
    private final UserService userService;
    private final BookService bookService;

    public SaveServiceImpl(SaveRepository saveRepository, UserService userService, BookService bookService) {
        this.saveRepository = saveRepository;
        this.userService = userService;
        this.bookService = bookService;
    }

    @Override
    public String save(Long userId, Long bookId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("user"));
        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("book"));
        Optional<Save> saveOptional = saveRepository.findByUser_IdAndBook_Id(userId, bookId);
        if (saveOptional.isEmpty()) {
            Save save = new Save();
            save.setBook(book.get());
            save.setUser(userOptional.get());
            saveRepository.save(save);
        }
        return "successfully saved";
    }

    @Override
    @Transactional
    public String unsave(Long userId, Long bookId) {
        Optional<Save> saveOptional = saveRepository.findByUser_IdAndBook_Id(userId, bookId);
        if (saveOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("save", "user and book"));
        saveRepository.deleteByUser_IdAndBook_Id(userId, bookId);
        return "successfully deleted";
    }
}
