package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ReReplyRequestDto;
import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.ReReply;
import com.sparta.homework4.repository.ReReplyRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ReReplyService;
import com.sparta.homework4.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReReplyController {
    private final ReReplyRepository reReplyRepository;
    private final ReReplyService reReplyService;

    @GetMapping("/api/contents/{contentsId}/reply/{replyId}")
    public List<ReReply> getReReply(@PathVariable(name = "contentsId") Long contentsId,
                                    @PathVariable(name = "replyId") Long replyId){
        return this.reReplyService.getReReply(replyId, contentsId);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping({"/api/contents/{contentsId}/reply/{replyId}"})
    public Response<String> createReReply(@PathVariable(name="contentsId") Long contentsId,
                                          @PathVariable(name="replyId") Long replyId,
                                          @RequestBody ReReplyRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails != null){
           Long userId = userDetails.getUser().getId();
           String username = userDetails.getUser().getUsername();
           return this.reReplyService.createReReply(requestDto, contentsId, replyId, username, userId);
        }else{
            return Response.success("로그인이 필요합니다.");
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping({"/api/contents/{contentsId}/reply/{replyId}/{id}"})
    public String updateReReply(@PathVariable(name="contentsId") Long contentsId,
                                @PathVariable(name="replyId") Long replyId,
                                @PathVariable(name="id") Long id,
                              @RequestBody ReReplyRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            String result = this.reReplyService.update(id, requestDto, username, userId);
            return result;
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping({"/api/contents/{contentsId}/reply/{replyId}/{id}"})
    public String deleteReply(@PathVariable(name="contentsId") Long contentsId,
                              @PathVariable(name="replyId") Long replyId,
                              @PathVariable(name="id") Long id,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            String result = this.reReplyService.deleteReply(id, replyId, userId);
            return result;
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    public ReReplyController(ReReplyRepository reReplyRepository, ReReplyService reReplyService){
        this.reReplyService = reReplyService;
        this.reReplyRepository = reReplyRepository;
    }
}
