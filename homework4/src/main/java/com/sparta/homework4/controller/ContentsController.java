package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ContentsRequestDto;
import com.sparta.homework4.dto.ContentsResponseDto;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ContentsService;
import com.sparta.homework4.util.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ContentsController {
    private final ContentsRepository ContentsRepository;
    private final ContentsService ContentsService;

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/api/contents"})
    public List<ContentsResponseDto> getContents() {
        return this.ContentsService.getContents();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/api/contents/{id}"})
    public Contents getContents(@PathVariable Long id) {
        Contents contents = (Contents)this.ContentsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return contents;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping({"/api/contents"})
    public Contents createContents(
            @RequestBody ContentsRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        Contents contents = this.ContentsService.createContents(requestDto, username);
        return contents;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/api/contents/{contentId}")
    public Response<String> updateContent(
            @PathVariable(name = "contentId") Long contentId,
            @RequestBody ContentsRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            return this.ContentsService.updateContent(contentId, requestDto, userDetails.getUsername());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/api/contents/{contentId}")
    public Response<String> deleteContent(
            @PathVariable(name = "contentId") Long contentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            return this.ContentsService.deleteContent(contentId, userDetails.getUsername());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/api/contents/{contentId}/user/{userId}/like")
    public Response<String> contentLike(
            @PathVariable(name = "contentId") Long contentId,
            @PathVariable(name = "userId") Long userId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
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