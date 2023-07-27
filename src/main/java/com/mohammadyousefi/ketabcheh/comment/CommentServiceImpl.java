package com.mohammadyousefi.ketabcheh.comment;

import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment findById(Long id) {
        Optional<Comment> commentOptional = commentRepository.findById(id);
        if(commentOptional.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("comment"));
        return commentOptional.get();
    }

    @Override
    public String comment(Comment comment) {
        commentRepository.save(comment);
        return "successfully added";
    }

    @Override
    public String deleteById(Long id) {
        Comment comment = findById(id);
        commentRepository.delete(comment);
        return "successfully deleted";
    }
}
