package com.mohammadyousefi.ketabcheh.category;

import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.exception.ErrorMessages;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) throw new NotFoundException(ErrorMessages.notFound("category"));
        return category.get();
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category save(CreateCategoryDto createCategoryDto) {
        Optional<Category> optionalCategory = findByName(createCategoryDto.getName());
        if(optionalCategory.isPresent()) throw new BadRequestException("a category with this name is already exist");
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        if (createCategoryDto.getParentCategoryId() != null) {
            Category parentCategory = findById(createCategoryDto.getParentCategoryId());
            category.setParentCategory(parentCategory);
        }
        return categoryRepository.save(category);
    }

    @Override
    public String deleteById(Long id) {
        findById(id);
        categoryRepository.deleteById(id);
        return "successfully deleted";
    }
}
