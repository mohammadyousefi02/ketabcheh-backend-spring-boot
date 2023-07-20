package com.mohammadyousefi.ketabcheh.category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> findAll();
    Category findById(Long id);
    Optional<Category> findByName(String name);
    Category save(CreateCategoryDto createCategoryDto);
    String deleteById(Long id);
}
