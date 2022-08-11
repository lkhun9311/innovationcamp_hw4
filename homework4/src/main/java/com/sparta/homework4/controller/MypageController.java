package com.sparta.homework4.controller;


import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.repository.ReplyRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.MypageService;
import com.sparta.homework4.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MypageController {

    private final MypageService MypageService;
    private final ReplyRepository ReplyRepository;

    public MypageController(MypageService mypageService, ReplyRepository replyRepository) {
        this.MypageService = mypageService;
        this.ReplyRepository = replyRepository;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/api/mypage/reply"})
    private List<Reply> getMyReply(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        if (userDetails != null) {
            String username = userDetails.getUser().getUsername();
            return this.MypageService.getMypageReply(username);
        }
        else{
            return null;
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/api/mypage/contents"})
    public List<Contents> getMypageContents(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(userDetails != null){
            Long userId = userDetails.getUser().getId();
            return this.MypageService.getMypageContents(userId);
        }
        else{
            return null;
        }
    }
}
