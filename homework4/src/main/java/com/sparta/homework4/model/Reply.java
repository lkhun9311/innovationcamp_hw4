package com.sparta.homework4.model;

import com.sparta.homework4.dto.ReplyRequestDto;

import javax.persistence.*;

@Entity
public class Reply extends Timestamped {
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Id
    private Long id;
    @Column(
            nullable = false
    )
    private Long postid;
    @Column(
            nullable = false
    )
    private String username;
    @Column(
            nullable = false
    )
    private String reply;
    @Column(
            nullable = false
    )
    private Long userId;

    public Reply(ReplyRequestDto requestDto, String username, Long userId) {
        this.postid = requestDto.getPostid();
        this.reply = requestDto.getReply();
        this.username = username;
        this.userId = userId;
    }

    public Reply(ReplyRequestDto requestDto, String username, Long userId, String reply) {
        this.postid = requestDto.getPostid();
        this.reply = reply;
        this.username = username;
        this.userId = userId;
    }

    public void update(ReplyRequestDto requestDto) {
        this.reply = requestDto.getReply();
    }

    public Long getId() {
        return this.id;
    }

    public Long getPostid() {
        return this.postid;
    }

    public String getUsername() {
        return this.username;
    }

    public String getReply() {
        return this.reply;
    }

    public Long getUserId() {
        return this.userId;
    }

    public Reply() {
    }
}
