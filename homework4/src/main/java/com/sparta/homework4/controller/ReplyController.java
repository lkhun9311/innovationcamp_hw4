package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.dto.ReplyResponseDto;
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

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/contents/{contentId}/replys"})
    public List<ReplyResponseDto> getReply(@PathVariable(name = "contentId") Long contentId) {
        return this.ReplyService.getReply(contentId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping({"/contents/{contentId}/replys"})
    public Response<String> createReply(@PathVariable(name = "contentId") Long contentId,
                                        @RequestBody ReplyRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            return this.ReplyService.createReply(contentId, requestDto, username, userId);
        } else {
            return Response.success("로그인이 필요합니다.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping({"/contents/{contentId}/replys/{replyId}"})
    public Response<String> updateReply(@PathVariable(name = "contentId") Long contentId,
                                        @RequestBody ReplyRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @PathVariable(name = "replyId") Long replyId) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.ReplyService.update(contentId, requestDto, userId, replyId);
        } else {
            return Response.success("로그인이 필요합니다.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping({"/contents/{contentId}/replys/{replyId}"})
    public Response<String> deleteReply(@PathVariable(name = "contentId") Long contentId,
                                        @PathVariable(name = "replyId") Long replyId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.ReplyService.deleteReply(contentId, replyId, userId);
        } else {
            return Response.success("로그인이 필요합니다.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/contents/{contentId}/replys/{replyId}/like")
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
