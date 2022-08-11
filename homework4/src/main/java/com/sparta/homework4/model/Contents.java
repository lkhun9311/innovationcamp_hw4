package com.sparta.homework4.model;

import com.sparta.homework4.dto.ContentsRequestDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Contents extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private long contentLikeCount;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_user_contents"))
    private User user;

    @OneToMany(fetch = LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<Reply> replyList = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ReReply> reReplyList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ContentLike> contentLikeList = new ArrayList<>();

    public Contents(String title, String username, String contents) {
        this.title = title;
        this.name = username;
        this.contents = contents;
    }

    public Contents(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getContents() {
        return this.contents;
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
        this.replyList.add(reply);
    }

    public void mapToReReply(ReReply reReply) {
        this.reReplyList.add(reReply);
    }

    public void mapToContentLike(ContentLike contentLike){
        this.contentLikeList.add(contentLike);
    }

    public void updateLikeCount(){
        this.contentLikeCount = (long) this.contentLikeList.size();
    }

    public void discountLike(ContentLike contentLike){
        this.contentLikeList.remove(contentLike);
    }
}
