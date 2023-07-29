package com.mohammadyousefi.ketabcheh.rate;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RateRepository extends JpaRepository<Rate, Long> {
    Optional<Rate> findByUser_IdAndBook_Id(Long userId, Long bookId);
}
