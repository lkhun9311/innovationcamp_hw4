package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ReplyService;
import com.sparta.homework4.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplyController {
    private final ReplyService ReplyService;

    @GetMapping({"/api/reply/{postId}"})
    public List<Reply> getReply(@PathVariable Long postId) {
        return this.ReplyService.getReply(postId);
    }

    @PostMapping({"/api/reply"})
    public String createReply(@RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            this.ReplyService.createReply(requestDto, username, userId);
            return "댓글 작성 완료";
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping({"/api/contents/{contentsId}/reply/{replyId}"})
    public Response<String> updateReply(@PathVariable(name = "contentsId") Long contentsId,
                                        @RequestBody ReplyRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PathVariable(name = "replyId") Long replyId) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.ReplyService.update(contentsId, requestDto, userId, replyId);
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping({"/api/contents/{contentsId}/replys/{replyId}"})
    public Response<String> deleteReply(@PathVariable(name = "contentsId") Long contentsId,
                                        @PathVariable(name = "replyId") Long replyId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.ReplyService.deleteReply(contentsId, replyId, userId);
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/api/contents/{contentId}/Reply/{replyId}/like")
    public Response<String> replyLike(@PathVariable(name = "contentId") Long contentId,
                                      @PathVariable(name = "replyId") Long replyId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            Long userId = userDetails.getUser().getId();
            ReplyService.replyLike(contentId, userId, replyId);
            return Response.success("success");
        }
    }

    public ReplyController(ReplyService ReplyService) {
        this.ReplyService = ReplyService;
    }
}
