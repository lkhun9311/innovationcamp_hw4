package com.sparta.homework4.model;

import com.sparta.homework4.dto.ReplyRequestDto;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Reply extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String reply;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_contents_reply"))
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_user_reply"))
    private Contents contents;

    @OneToMany(fetch = LAZY, mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReReply> reReplyList = new ArrayList<>();

    public Long getUserId() {
        return user.getId();
    }

    public String getUsername() {
        return this.username;
    }

    public Reply() {
    }

    public Reply(String reply, String username) {
        this.reply = reply;
        this.username = username;
    }

    public Reply(ReplyRequestDto requestDto, String username) {
        this.reply = requestDto.getReply();
        this.username = username;
    }

    public void update(ReplyRequestDto requestDto) {
        this.reply = requestDto.getReply();
    }

    public void mapToReReply(ReReply reReply) {
        this.reReplyList.add(reReply);
    }

    public void mapToContentsAndUser(Contents contents, User user) {
        this.contents = contents;
        this.user = user;

        contents.mapToReply(this);
        user.mapToReply(this);
    }
}
