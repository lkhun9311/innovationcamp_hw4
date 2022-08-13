package com.sparta.homework4.repository;

import com.sparta.homework4.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReReplyLikeRepository extends JpaRepository<ReReplyLike, Long> {
    Optional<ReReplyLike> findByReReplyAndReplyAndContentsAndUser(ReReply reReply, Reply reply, Contents contents, User user);
}
