package com.sparta.homework4.dto;

public class ReplyRequestDto {
    private Long postid;
    private String reply;

    public ReplyRequestDto() {
    }

    public Long getPostid() {
        return this.postid;
    }

    public String getReply() {
        return this.reply;
    }
}
