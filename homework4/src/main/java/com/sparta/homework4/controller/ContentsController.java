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
    @GetMapping({"/contents"})
    public List<ContentsResponseDto> getContents() {
        return this.ContentsService.getContents();
    }

    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping({"/contents/{contentId}"})
    public Contents getContents(@PathVariable(name = "contentId") Long contentId) {
        Contents contents = this.ContentsRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));
        return contents;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping({"/contents"})
    public Response<String> createContents(@RequestBody ContentsRequestDto requestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            Long userId = userDetails.getUser().getId();
            String username = userDetails.getUser().getUsername();
            return this.ContentsService.createContents(requestDto, username, userId);
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PutMapping("/contents/{contentId}")
    public Response<String> updateContent(@PathVariable(name = "contentId") Long contentId,
                                          @RequestBody ContentsRequestDto requestDto,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            return this.ContentsService.updateContent(contentId, requestDto, userDetails.getUsername());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping("/contents/{contentId}")
    public Response<String> deleteContent(@PathVariable(name = "contentId") Long contentId,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            return this.ContentsService.deleteContent(contentId, userDetails.getUsername());
        }
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/contents/{contentId}/like")
    public Response<String> contentLike(@PathVariable(name = "contentId") Long contentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            Long userId = userDetails.getUser().getId();
            ContentsService.contentLike(contentId, userId);
            return Response.success("success");
        }
    }

    public ContentsController(ContentsRepository contentsRepository, ContentsService contentsService) {
        this.ContentsRepository = contentsRepository;
        this.ContentsService = contentsService;
    }
}