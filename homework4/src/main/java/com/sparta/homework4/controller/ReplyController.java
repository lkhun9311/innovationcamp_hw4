package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.repository.ReplyRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ReplyService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplyController {
    private final ReplyRepository ReplyRepository;
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

    @PutMapping({"/api/reply/{id}"})
    public String updateReply(@PathVariable Long id, @RequestBody ReplyRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            String result = this.ReplyService.update(id, requestDto, username, userId);
            return result;
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @DeleteMapping({"/api/reply/{replyId}"})
    public String deleteReply(@PathVariable Long replyId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String result = this.ReplyService.deleteReply(replyId, userId);
            return result;
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    public ReplyController(com.sparta.homework4.repository.ReplyRepository ReplyRepository, com.sparta.homework4.service.ReplyService ReplyService) {
        this.ReplyRepository = ReplyRepository;
        this.ReplyService = ReplyService;
    }
}
