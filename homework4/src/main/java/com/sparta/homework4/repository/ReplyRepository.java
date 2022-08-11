package com.sparta.homework4.repository;

import com.sparta.homework4.model.Reply;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByPostidOrderByCreatedAtDesc(Long var1);

    int countByPostid(Long var1);
}
