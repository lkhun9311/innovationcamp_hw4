package com.sparta.homework4.model;

import com.sparta.homework4.dto.ReReplyRequestDto;

import javax.persistence.*;

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

    public Long getId() {
        return this.id;
    }

    public Long getUserId() { return user.getId(); }

    public String getUsername() {
        return this.username;
    }

    public String getReReply() {
        return this.reReply;
    }

    public ReReply() {}

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

    public void mapToUser(User user) {
        this.user = user;
        user.mapToReReply(this);
    }

    public void mapToContents(Contents contents) {
        this.contents = contents;
        contents.mapToReReply(this);
    }

    public void mapToReply(Reply reply) {
        this.reply = reply;
        reply.mapToReReply(this);
    }

    public void mapToUserRemove(User user) {
        this.user = user;
        user.mapToReReplyRemove(this);
    }

    public void mapToContentsRemove(Contents contents) {
        this.contents = contents;
        contents.mapToReReplyRemove(this);
    }

    public void mapToReplyRemove(Reply reply) {
        this.reply = reply;
        reply.mapToReReplyRemove(this);
    }
}
