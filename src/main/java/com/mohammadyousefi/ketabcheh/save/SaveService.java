package com.mohammadyousefi.ketabcheh.save;

public interface SaveService {
    String save(Long userId, Long bookId);
    String unsave(Long userId, Long bookId);
}
