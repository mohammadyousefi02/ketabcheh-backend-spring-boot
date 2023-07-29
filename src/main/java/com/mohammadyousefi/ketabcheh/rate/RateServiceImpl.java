package com.mohammadyousefi.ketabcheh.rate;

import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.book.BookService;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {
    private final RateRepository rateRepository;
    private final BookService bookService;

    public RateServiceImpl(RateRepository rateRepository, BookService bookService) {
        this.rateRepository = rateRepository;
        this.bookService = bookService;
    }

    @Override
    public String save(RateDto rateDto) {
        Optional<Rate> rateOptional = rateRepository.findByUser_IdAndBook_Id(rateDto.getUserId(), rateDto.getBookId());
        if(rateOptional.isPresent()) {
            rateOptional.get().setRate(rateDto.getRate());
            rateRepository.save(rateOptional.get());
        }else  {
            Optional<Book> bookOptional = bookService.findById(rateDto.getBookId());
            if(bookOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("book"));
            Rate rate = new Rate();
            rate.setBook(bookOptional.get());
            User user = new User();
            user.setId(rateDto.getUserId());
            rate.setUser(user);
            rate.setRate(rateDto.getRate());
            rateRepository.save(rate);
        }
        return "rating submitted successfully";
    }
}
