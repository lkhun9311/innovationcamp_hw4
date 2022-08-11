package com.sparta.homework4.repository;

import com.sparta.homework4.model.ReReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {
    List<ReReply> findAllByReplyIdAndContentsIdOrderByCreatedAtDesc(Long replyId, Long contentsId);

    Long countByReplyId(Long replyId);
}
