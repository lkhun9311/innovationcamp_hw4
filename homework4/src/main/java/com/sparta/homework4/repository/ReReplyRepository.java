package com.sparta.homework4.repository;

import com.sparta.homework4.model.ReReply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReReplyRepository extends JpaRepository<ReReply, Long> {
    // ReplyId로 검색, 작성일순
    List<ReReply> findAllByReplyIdOrderByCreatedAtDesc(Long var1);

    int countByReplyId(Long var1);
}