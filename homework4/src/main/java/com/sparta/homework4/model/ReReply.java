package com.sparta.homework4.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.homework4.dto.ReReplyRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@RequiredArgsConstructor
@Entity
public class ReReply extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String reReply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", foreignKey = @ForeignKey(name = "FK_rereply_reply"))
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_rereply_contents"))
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_rereply_user"))
    private User user;

    public ReReply(ReReplyRequestDto requestDto, String username) {
        this.reReply = requestDto.getReReply();
        this.username = username;
    }

    public ReReply(String reReply, String username) {
        this.reReply = reReply;
        this.username = username;
    }

    public void update(ReReplyRequestDto requestDto) {
        this.reReply = requestDto.getReReply();
    }

    public void mapToReplyAndContentsAndUser(Reply reply, Contents contents, User user) {
        this.reply = reply;
        this.contents = contents;
        this.user = user;
    }
}
