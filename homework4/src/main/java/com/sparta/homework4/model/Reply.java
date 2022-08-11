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

    @Column(nullable = false)
    private long replyLikeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_contents_reply"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_user_reply"))
    private Contents contents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReReply> reReplyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    public Long getUserId() {
        return user.getId();
    }

    public String getUsername() {
        return this.username;
    }

    public String getReply() {
        return this.reply;
    }

    public Long getReplyLikeCount() {
        return this.replyLikeCount;
    }

    public Reply() {
    }

    public Reply(String reply, String username) {
        this.reply = reply;
        this.username = username;
    }

    public Reply(String reply, String username, Long replyLikeCount) {
        this.reply = reply;
        this.username = username;
        this.replyLikeCount = replyLikeCount;
    }

    public Reply(ReplyRequestDto requestDto, String username) {
        this.reply = requestDto.getReply();
        this.username = username;
    }

    public void update(ReplyRequestDto requestDto) {
        this.reply = requestDto.getReply();
    }

    public void mapToUser(User user) {
        this.user = user;
        user.mapToReply(this);
    }

    public void mapToReReply(ReReply reReply) {
        this.reReplyList.add(reReply);
    }

    public void mapToContents(Contents contents) {
        this.contents = contents;
        contents.mapToReply(this);
    }

    public void mapToUserRemove(User user) {
        this.user = user;
        user.mapToReplyRemove(this);
    }

    public void mapToContentsRemove(Contents contents) {
        this.contents = contents;
        contents.mapToReplyRemove(this);
    }

    public void mapToReplyLike(ReplyLike replyLike){ this.replyLikeList.add(replyLike); }

    public void updateLikeCount(){ this.replyLikeCount = (long) this.replyLikeList.size(); }

    public void discountLike(ReplyLike replyLike){
        this.replyLikeList.remove(replyLike);
    }
}
