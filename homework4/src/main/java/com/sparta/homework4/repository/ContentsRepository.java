package com.sparta.homework4.repository;

import com.sparta.homework4.model.Contents;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    List<Contents> findAllByOrderByCreatedAtDesc();

    List<Contents> findAllByUserIdOrderByCreatedAtDesc(Long userId);
}
