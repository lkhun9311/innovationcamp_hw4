package com.sparta.homework4.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class ReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ReplyLike_User"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_ReplyLike_Contents"))
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", foreignKey = @ForeignKey(name = "FK_ReplyLike_Reply"))
    private Reply reply;

    public void mapToUser(User user){
        this.user = user;
        user.mapToReplyLike(this);
    }

    public void mapToContent(Contents contents){
        this.contents = contents;
        contents.mapToReplyLike(this);
    }

    public void mapToReply(Reply reply){
        this.reply = reply;
        reply.mapToReplyLike(this);
    }
}
