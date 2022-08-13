package com.sparta.homework4.model;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class ReReplyLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_ReReplyLike_User"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", foreignKey = @ForeignKey(name = "FK_ReReplyLike_Contents"))
    private Contents contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reply_id", foreignKey = @ForeignKey(name = "FK_ReReplyLike_Reply"))
    private Reply reply;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reReply_id", foreignKey = @ForeignKey(name = "FK_ReReplyLike_ReReply"))
    private ReReply reReply;

    public void mapToUser(User user){
        this.user = user;
        user.mapToReReplyLike(this);
    }

    public void mapToContent(Contents contents){
        this.contents = contents;
        contents.mapToReReplyLike(this);
    }

    public void mapToReply(Reply reply){
        this.reply = reply;
        reply.mapToReReplyLike(this);
    }

    public void mapToReReply(ReReply reReply){
        this.reReply = reReply;
        reReply.mapToReReplyLike(this);
    }
}
