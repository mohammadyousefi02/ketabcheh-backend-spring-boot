package com.mohammadyousefi.ketabcheh.comment;

public interface CommentService {
    Comment findById(Long id);

    String comment(Comment comment);

    String deleteById(Long id);
}
