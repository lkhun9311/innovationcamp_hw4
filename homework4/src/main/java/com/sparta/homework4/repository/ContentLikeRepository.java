package com.sparta.homework4.repository;

import com.sparta.homework4.model.ContentLike;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContentLikeRepository extends JpaRepository<ContentLike, Long> {
    Optional<ContentLike> findByContentsAndUser(Contents contents, User user);
}
