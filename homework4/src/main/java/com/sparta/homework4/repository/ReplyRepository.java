package com.sparta.homework4.repository;

import com.sparta.homework4.model.ContentLike;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.Reply;

import java.util.List;
import java.util.Optional;

import com.sparta.homework4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByContentsIdOrderByCreatedAtDesc(Long contentsId);

//    Optional<Reply> findByReplyAndUser(Reply reply, User user);

    Long countByContentsId(Long contentsId);
}
