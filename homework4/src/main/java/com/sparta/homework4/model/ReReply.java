package com.sparta.homework4.model;

import com.sparta.homework4.dto.ReReplyRequestDto;
import com.sparta.homework4.dto.ReplyRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Getter
@RequiredArgsConstructor
@Entity
public class ReReply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String reReply;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private Long replyId;

    @Column(nullable = false)
    private Long userId;


    public ReReply(ReReplyRequestDto requestDto, String username, Long userId) {
        this.postId = requestDto.getPostid();
        this.replyId = requestDto.getReplyId();
        this.reReply = requestDto.getReReply();
        this.username = username;
        this.userId = userId;
    }

    public ReReply(ReReplyRequestDto requestDto, String username, Long userId, String reReply) {
        this.postId = requestDto.getPostid();
        this.replyId = requestDto.getReplyId();
        this.reReply = requestDto.getReReply();
        this.username = username;
        this.userId = userId;
    }

    public void update(ReReplyRequestDto requestDto) {
        this.reReply = requestDto.getReReply();
    }
}
