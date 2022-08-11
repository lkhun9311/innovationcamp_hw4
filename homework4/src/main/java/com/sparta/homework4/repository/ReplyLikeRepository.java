package com.sparta.homework4.repository;

import com.sparta.homework4.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepository extends JpaRepository<ReplyLike, Long> {
    Optional<ReplyLike> findByReplyAndContentsAndUser(Reply reply, Contents contents, User user);
}
