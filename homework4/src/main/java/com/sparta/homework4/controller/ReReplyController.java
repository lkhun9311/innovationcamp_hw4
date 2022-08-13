package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ReReplyRequestDto;
import com.sparta.homework4.model.ReReply;
import com.sparta.homework4.repository.ReReplyRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ReReplyService;
import com.sparta.homework4.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReReplyController {
    private final ReReplyRepository reReplyRepository;
    private final ReReplyService reReplyService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping("/api/contents/{contentsId}/replys/{replyId}/rereplys")
    public List<ReReply> getReReply(@PathVariable(name = "contentsId") Long contentsId, @PathVariable(name = "replyId") Long replyId){
        return this.reReplyService.getReReply(replyId, contentsId);
    }

    @Transactional
    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping({"/api/contents/{contentsId}/replys/{replyId}/rereplys"})
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

    @Transactional
    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping({"/api/contents/{contentsId}/replys/{replyId}/rereplys/{rereplyId}"})
    public String updateReReply(@PathVariable(name="contentsId") Long contentsId,
                                @PathVariable(name="replyId") Long replyId,
                                @PathVariable(name="rereplyId") Long rereplyId,
                              @RequestBody ReReplyRequestDto requestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.reReplyService.update(rereplyId, requestDto, userId, contentsId, replyId);
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @Transactional
    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping({"/api/contents/{contentsId}/replys/{replyId}/rereplys/{rereplyId}"})
    public String deleteReply(@PathVariable(name="contentsId") Long contentsId,
                              @PathVariable(name="replyId") Long replyId,
                              @PathVariable(name="rereplyId") Long rereplyId,
                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            Long userId = userDetails.getUser().getId();
            return this.reReplyService.deleteReply(rereplyId, replyId, contentsId, userId);
        } else {
            return "로그인이 필요한 기능입니다.";
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/api/contents/{contentId}/replys/{replyId}/rereplys/{rereplyId}/like")
    public Response<String> reReplyLike(@PathVariable(name = "contentId") Long contentId,
                                      @PathVariable(name = "replyId") Long replyId,
                                      @PathVariable(name = "rereplyId") Long reReplyId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            Long userId = userDetails.getUser().getId();
            reReplyService.reReplyLike(contentId, userId, replyId, reReplyId);
            return Response.success("success");
        }
    }

    public ReReplyController(ReReplyRepository reReplyRepository, ReReplyService reReplyService){
        this.reReplyService = reReplyService;
        this.reReplyRepository = reReplyRepository;
    }
}
