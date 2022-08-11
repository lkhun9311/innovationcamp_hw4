package com.sparta.homework4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.homework4.model.Reply;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReplyResponseDto {
    private Long id;
    private String username;
    private String reply;
    private Long countReReply;
    private Long replyLikeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    @Builder
    public ReplyResponseDto(Reply reply, Long countReReply) {
        this.id = reply.getId();
        this.username = reply.getUsername();
        this.reply = reply.getReply();
        this.countReReply = countReReply;
        this.replyLikeCount = reply.getReplyLikeCount();
        this.createdAt = reply.getCreatedAt();
        this.modifiedAt = reply.getModifiedAt();
    }
}
