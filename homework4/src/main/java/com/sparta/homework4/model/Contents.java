package com.sparta.homework4.model;

import com.sparta.homework4.dto.ContentsRequestDto;

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
    private long contentLikeCount;

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

    public Contents(ContentsRequestDto requestDto, String username) {
        this.title = requestDto.getTitle();
        this.name = username;
        this.contents = requestDto.getContents();
    }

    public void update(ContentsRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.name = requestDto.getName();
        this.contents = requestDto.getContents();
    }

    public Contents(ContentsRequestDto requestDto, String username, String contents) {
        this.title = requestDto.getTitle();
        this.name = username;
        this.contents = contents;
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

    public Contents() {
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "contents", cascade = CascadeType.REMOVE)
    private List<ContentLike> contentLikeList = new ArrayList<>();

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
