package com.mohammadyousefi.ketabcheh.save;

import java.util.List;

public interface SaveService {
    List<Save> findByUserId(Long userId);
    String save(Long userId, Long bookId);
    String unsave(Long userId, Long bookId);
}
