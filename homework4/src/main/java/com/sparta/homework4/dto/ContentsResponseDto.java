package com.sparta.homework4.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.homework4.model.Contents;

import java.time.LocalDateTime;

public class ContentsResponseDto {
    private Long id;
    private String title;
    private String name;
    private String contents;
    private String image;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss"
    )
    private final LocalDateTime modifiedAt;
    private int countReply;

    public ContentsResponseDto(Contents content, int countReply) {
        this.id = content.getId();
        this.title = content.getTitle();
        this.name = content.getName();
        this.contents = content.getContents();
        this.image = content.getImage();
        this.modifiedAt = content.getModifiedAt();
        this.countReply = countReply;
    }

    public static ContentsResponseDtoBuilder builder() {
        return new ContentsResponseDtoBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getContents() {
        return this.contents;
    }

    public String getImage() {
        return this.image;
    }


    public LocalDateTime getModifiedAt() {
        return this.modifiedAt;
    }

    public int getCountReply() {
        return this.countReply;
    }

    public static class ContentsResponseDtoBuilder {
        private Contents content;
        private int countReply;

        ContentsResponseDtoBuilder() {
        }

        public ContentsResponseDtoBuilder content(Contents content) {
            this.content = content;
            return this;
        }

        public ContentsResponseDtoBuilder countReply(int countReply) {
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
