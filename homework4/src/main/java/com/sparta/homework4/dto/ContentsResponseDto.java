package com.sparta.homework4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.homework4.model.Contents;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ContentsResponseDto {
    private Long id;
    private String title;
    private String name;
    private String contents;
    private Long countReply;
    private Long countReReply;
    private Long contentLikeCount;
    private Long replyLikeCount;

    private Long reReplyLikeCount;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;

    @Builder
    public ContentsResponseDto(Contents content, Long countReply) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.name = content.getName();
        this.contents = content.getContents();
        this.contentLikeCount = content.getContentLikeCount();
        this.countReply = countReply;
        this.countReReply = content.getCountReReply();
        this.replyLikeCount = content.getReplyLikeCount();
        this.reReplyLikeCount = content.getReReplyLikeCount();
        this.createdAt = content.getCreatedAt();
        this.modifiedAt = content.getModifiedAt();
    }
}
