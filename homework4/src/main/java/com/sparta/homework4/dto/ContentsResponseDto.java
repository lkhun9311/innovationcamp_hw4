package com.sparta.homework4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.homework4.model.Contents;

import java.time.LocalDateTime;

public class ContentsResponseDto {
    private Long id;
    private String title;
    private String name;
    private String contents;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime modifiedAt;
    private Long countReply;

    public ContentsResponseDto(Contents content, Long countReply) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.name = content.getName();
        this.contents = content.getContents();
        this.modifiedAt = content.getModifiedAt();
        this.countReply = countReply;
    }

    public static ContentsResponseDtoBuilder builder() {
        return new ContentsResponseDtoBuilder();
    }

    public static class ContentsResponseDtoBuilder {
        private Contents content;
        private Long countReply;

        ContentsResponseDtoBuilder() {
        }

        public ContentsResponseDtoBuilder content(Contents content) {
            this.content = content;
            return this;
        }

        public ContentsResponseDtoBuilder countReply(Long countReply) {
            this.countReply = countReply;
            return this;
        }

        public ContentsResponseDto build() {
            return new ContentsResponseDto(this.content, this.countReply);
        }

        public String toString() {
            return "ContentsResponseDto.ContentsResponseDtoBuilder(content=" + this.content + ", countReply=" + this.countReply + ")";
        }
    }
}
