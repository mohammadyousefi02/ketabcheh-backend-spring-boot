package com.mohammadyousefi.ketabcheh.author;

import com.mohammadyousefi.ketabcheh.exception.BadRequestException;
import com.mohammadyousefi.ketabcheh.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Optional<Author> findByName(String name) {
        return authorRepository.findByName(name);
    }

    @Override
    public Author save(CreateAuthorDto createAuthorDto) {
        Optional<Author> optionalAuthor = findByName(createAuthorDto.getName());
        if(optionalAuthor.isPresent()) throw new BadRequestException("this author is already existed");
        Author author = new Author();
        author.setName(createAuthorDto.getName());
        return authorRepository.save(author);
    }

    @Override
    public String deleteById(Long id) {
        Optional<Author> author = findById(id);
        if(author.isEmpty()) throw new NotFoundException("there is no author with this id");
        authorRepository.deleteById(id);
        return "successfully deleted";
    }
}
