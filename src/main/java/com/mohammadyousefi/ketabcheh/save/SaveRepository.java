package com.mohammadyousefi.ketabcheh.save;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SaveRepository extends JpaRepository<Save, Long> {
    List<Save> findByUser_Id(Long userId);
    Optional<Save> findByUser_IdAndBook_Id(Long userId, Long bookId);

    void deleteByUser_IdAndBook_Id(Long userId, Long bookId);
}
