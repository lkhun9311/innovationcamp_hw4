package com.sparta.homework4.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ReReplyRequestDto {
    private Long postid;
    private Long replyId;
    private String reReply;
}
