package com.sparta.homework4.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Contents> contentList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ReReply> reReplyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ContentLike> contentLikeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<ReReplyLike> reReplyLikeList = new ArrayList<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void mapToContents(Contents contents) {
        contentList.add(contents);
    }

    public void mapToReply(Reply reply) {
        replyList.add(reply);
    }

    public void mapToReplyRemove(Reply reply) { replyList.remove(reply); }

    public void mapToReReply(ReReply reReply) { reReplyList.add(reReply); }

    public void mapToReReplyRemove(ReReply reReply) { replyList.remove(reReply); }

    public void mapToContentLike(ContentLike contentLike) {
        this.contentLikeList.add(contentLike);
    }

    public void mapToReplyLike(ReplyLike replyLike) { this.replyLikeList.add(replyLike); }

    public void discountReplyLike(ReplyLike replyLike) { this.replyLikeList.remove(replyLike); }

    public void mapToReReplyLike(ReReplyLike reReplyLike) { this.reReplyLikeList.add(reReplyLike); }

    public void discountReReplyLike(ReReplyLike reReplyLike) { this.reReplyLikeList.remove(reReplyLike); }
}
