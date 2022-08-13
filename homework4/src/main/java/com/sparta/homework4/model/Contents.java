package com.sparta.homework4.model;

import com.sparta.homework4.dto.ContentsRequestDto;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Contents extends Timestamped {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private long countReply;

    @Column(nullable = false)
    private long countReReply;

    @Column(nullable = false)
    private long contentLikeCount;

    @Column(nullable = false)
    private long replyLikeCount;

    @Column(nullable = false)
    private long reReplyLikeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_contents"))
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ReReply> reReplyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ContentLike> contentLikeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ReplyLike> replyLikeList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ReReplyLike> reReplyLikeList = new ArrayList<>();

    public Contents(String title, String username, String contents) {
        this.title = title;
        this.name = username;
        this.contents = contents;
    }

    public Contents(String title,
                    String username,
                    String contents,
                    Long countReply,
                    Long countReReply,
                    Long contentLikeCount,
                    Long replyLikeCount,
                    Long reReplyLikeCount) {
        this.title = title;
        this.name = username;
        this.contents = contents;
        this.countReply = countReply;
        this.countReReply = countReReply;
        this.contentLikeCount = contentLikeCount;
        this.replyLikeCount = replyLikeCount;
        this.reReplyLikeCount = reReplyLikeCount;
    }

    public Contents(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
    }

    public Long getId() { return this.id; }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getContents() {
        return this.contents;
    }

    public Long getCountReply() {
        return this.countReply;
    }

    public Long getCountReReply() {
        return this.countReReply;
    }

    public Long getContentLikeCount() {
        return this.contentLikeCount;
    }

    public Long getReplyLikeCount() {
        return this.replyLikeCount;
    }

    public Long getReReplyLikeCount() {
        return this.reReplyLikeCount;
    }

    public Contents() {}

    public Contents(ContentsRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.name = username;
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsRequestDto requestDto, String username, String contents) {
        this.title = requestDto.getTitle();
        this.name = username;
        this.contents = contents;
    }

    public void update(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.contents = requestDto.getContents();
    }

    public void mapToUser(User user) {
        this.user = user;
        user.mapToContents(this);
    }

    public void mapToReply(Reply reply) {
        replyList.add(reply);
    }

    public void mapToReplyRemove(Reply reply) { replyList.remove(reply); }

    public void mapToReReply(ReReply reReply) { reReplyList.add(reReply); }

    public void mapToReReplyRemove(ReReply reReply) { reReplyList.remove(reReply); }

    public void mapToContentLike(ContentLike contentLike) {
        this.contentLikeList.add(contentLike);
    }

    public void mapToReplyLike(ReplyLike replyLike) {
        this.replyLikeList.add(replyLike);
    }

    public void mapToReReplyLike(ReReplyLike reReplyLike) { this.reReplyLikeList.add(reReplyLike); }

    public void updateReplyCount() { this.countReply = (long) this.replyList.size(); }

    public void updateReReplyCount() { this.countReReply = (long) this.reReplyList.size(); }

    public void updateLikeCount() { this.contentLikeCount = (long) this.contentLikeList.size(); }

    public void updateReplyLikeCount() { this.replyLikeCount = (long) this.replyLikeList.size(); }

    public void updateReReplyLikeCount() { this.reReplyLikeCount = (long) this.reReplyLikeList.size(); }

    public void discountLike(ContentLike contentLike) { this.contentLikeList.remove(contentLike); }

    public void discountReplyLike(ReplyLike replyLike) { this.replyLikeList.remove(replyLike);}

    public void discountReReplyLike(ReReplyLike reReplyLike) { this.reReplyLikeList.remove(reReplyLike); }
}

