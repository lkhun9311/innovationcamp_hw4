package com.sparta.homework4.controller;

import com.sparta.homework4.dto.ContentsRequestDto;
import com.sparta.homework4.dto.ContentsResponseDto;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.ContentsService;
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

    public ContentsController(com.sparta.homework4.repository.ContentsRepository ContentsRepository, com.sparta.homework4.service.ContentsService ContentsService) {
        this.ContentsRepository = ContentsRepository;
        this.ContentsService = ContentsService;
    }
}