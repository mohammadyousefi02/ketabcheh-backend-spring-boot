package com.mohammadyousefi.ketabcheh.comment;

import com.mohammadyousefi.ketabcheh.auth.Authorization;
import com.mohammadyousefi.ketabcheh.book.Book;
import com.mohammadyousefi.ketabcheh.book.BookService;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import com.mohammadyousefi.ketabcheh.response.Response;
import com.mohammadyousefi.ketabcheh.user.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;
    private final BookService bookService;

    public CommentController(CommentService commentService, BookService bookService) {
        this.commentService = commentService;
        this.bookService = bookService;
    }

    @PostMapping("/{bookId}")
    @Authorization
    public Response<String> comment(HttpServletRequest request, @PathVariable Long bookId, @Validated @RequestBody CommentDto commentDto) {
        Optional<Book> bookOptional = bookService.findById(bookId);
        if (bookOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("book"));

        return new Response<>(saveComment(request, bookOptional.get(), commentDto.getMessage(), null), HttpStatus.CREATED.value());
    }

    @PostMapping("/reply/{commentId}")
    @Authorization
    public Response<String> reply(HttpServletRequest request, @PathVariable Long commentId, @Validated @RequestBody CommentDto commentDto) {
        Comment comment = commentService.findById(commentId);
        return new Response<>(saveComment(request, comment.getBook(), commentDto.getMessage(), comment), HttpStatus.CREATED.value());
    }

    @DeleteMapping("/{commentId}")
    @Authorization
    public Response<String> deleteById(@PathVariable Long commentId) {
        return new Response<>(commentService.deleteById(commentId));
    }

    private String saveComment(HttpServletRequest request, Book book, String message, Comment replyTo) {
        Comment comment = new Comment();
        comment.setBook(book);
        User user = new User();
        user.setId((Long) request.getAttribute("userId"));
        comment.setUser(user);
        comment.setMessage(message);
        if (replyTo != null) comment.setReplyTo(replyTo);
        return commentService.comment(comment);
    }
}
