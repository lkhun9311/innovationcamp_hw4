package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.repository.ReplyRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ReplyService;
import com.sparta.homework4.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReplyController {
    private final ReplyRepository ReplyRepository;
    private final ReplyService ReplyService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/api/contents/{contentsId}/reply"})
    public List<Reply> getReply(@PathVariable Long contentsId) {
        return this.ReplyService.getReply(contentsId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping({"/api/contents/{contentsId}/reply"})
    public Response<String> createReply(@PathVariable(name = "contentsId") Long contentsId,
                                        @RequestBody ReplyRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            return this.ReplyService.createReply(contentsId, requestDto, username, userId);
        } else {
            return Response.success("로그인이 필요합니다.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping({"/api/contents/{contentsId}/reply"})
    public Response<String> updateReply(@PathVariable(name = "contentsId") Long contentsId,
                              @RequestBody ReplyRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            return this.ReplyService.update(contentsId, requestDto, username, userId);
        } else {
            return Response.success("로그인이 필요합니다.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping({"/api/contents/{contentsId}/reply"})
    public Response<String> deleteReply(@PathVariable(name = "contentsId") Long contentsId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.ReplyService.deleteReply(contentsId, userId);
        } else {
            return Response.success("로그인이 필요합니다.");
        }
    }

    public ReplyController(ReplyRepository ReplyRepository, ReplyService ReplyService) {
        this.ReplyRepository = ReplyRepository;
        this.ReplyService = ReplyService;
    }
}
