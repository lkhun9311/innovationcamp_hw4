package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ContentsRequestDto;
import com.sparta.homework4.dto.ContentsResponseDto;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ContentsService;
import com.sparta.homework4.util.response.Response;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentsController {
    private final ContentsRepository ContentsRepository;
    private final ContentsService ContentsService;

    @GetMapping({"/api/contents"})
    public List<ContentsResponseDto> getContents() {
        return this.ContentsService.getContents();
    }

    @GetMapping({"/api/contents/{id}"})
    public Contents getContents(@PathVariable Long id) {
        Contents contents = (Contents)this.ContentsRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("id가 존재하지 않습니다.");
        });
        return contents;
    }

    @PostMapping({"/api/contents"})
    public Contents createContents(@RequestBody ContentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        Contents contents = this.ContentsService.createContents(requestDto, username);
        return contents;
    }

    @PutMapping("/api/contents/{contentId}")
    public Response<String> updateContent(@PathVariable(name = "contentId") Long contentId, @RequestBody ContentsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            Long updateId = this.ContentsService.updateContent(contentId, requestDto);
            return Response.success("Id " + updateId + "번 게시글의 내용을 변경했습니다.");
        }
    }

    @PostMapping("/api/contents/{contentId}/user/{userId}/like")
    public Response<String> contentLike(@PathVariable(name = "contentId") Long contentId, @PathVariable(name = "userId") Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            ContentsService.contentLike(contentId, userId);
            return Response.success("success");
        }
    }

    public ContentsController(ContentsRepository contentsRepository, ContentsService contentsService) {
        this.ContentsRepository = contentsRepository;
        this.ContentsService = contentsService;
    }
}