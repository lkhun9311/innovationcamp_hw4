package com.sparta.homework4.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class ContentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ContentLike_User"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_ContentLike_Contents"))
    private Contents contents;

    public void mapToUser(User user){
        this.user = user;
        user.mapToContentLike(this);
    }

    public void mapToContent(Contents contents){
        this.contents = contents;
        contents.mapToContentLike(this);
    }
}
