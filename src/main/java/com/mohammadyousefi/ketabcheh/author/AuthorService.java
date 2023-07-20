package com.mohammadyousefi.ketabcheh.author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<Author> findAll();
    Optional<Author> findById(Long id);
    Optional<Author> findByName(String name);
    Author save(CreateAuthorDto createAuthorDto);
    String deleteById(Long id);
}
