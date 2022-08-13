package com.sparta.homework4.model;

import com.sparta.homework4.dto.ReplyRequestDto;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

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
    private long countReReply;

    @Column(nullable = false)
    private long replyLikeCount;

    @Column(nullable = false)
    private long reReplyLikeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_reply_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_reply_contents"))
    private Contents contents;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReReply> reReplyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "reply", cascade = CascadeType.REMOVE)
    private List<ReReplyLike> reReplyLikeList = new ArrayList<>();

    public Long getId() {
        return this.id;
    }

    public Long getUserId() {
        return user.getId();
    }

    public String getUsername() {
        return this.username;
    }

    public String getReply() {
        return this.reply;
    }

    public Long getCountReReply() {
        return this.countReReply;
    }

    public Long getReplyLikeCount() {
        return this.replyLikeCount;
    }

    public Long getReReplyLikeCount() {
        return this.reReplyLikeCount;
    }

    public Reply() {}

    public Reply(String reply, String username) {
        this.reply = reply;
        this.username = username;
    }

    public Reply(String reply, String username, Long replyLikeCount, Long reReplyLikeCount) {
        this.reply = reply;
        this.username = username;
        this.replyLikeCount = replyLikeCount;
        this.reReplyLikeCount = reReplyLikeCount;
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

    public void mapToReReply(ReReply reReply) { this.reReplyList.add(reReply); }

    public void mapToReReplyRemove(ReReply reReply) {
        this.reReplyList.remove(reReply);
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

    public void mapToReReplyLike(ReReplyLike reReplyLike) { this.reReplyLikeList.add(reReplyLike); }

    public void updateReReplyCount() { this.countReReply = (long) this.reReplyList.size(); }

    public void updateLikeCount(){ this.replyLikeCount = (long) this.replyLikeList.size(); }

    public void updateReReplyLikeCount() {
        this.reReplyLikeCount = (long) this.reReplyLikeList.size();
    }

    public void discountLike(ReplyLike replyLike){
        this.replyLikeList.remove(replyLike);
    }

    public void discountReReplyLike(ReReplyLike reReplyLike){ this.reReplyLikeList.remove(reReplyLike); }


}
