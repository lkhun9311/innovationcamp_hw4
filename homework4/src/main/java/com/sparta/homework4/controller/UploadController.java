package com.sparta.homework4.controller;

import com.sparta.homework4.security.UserDetailsImpl;
import com.sparta.homework4.service.S3Uploader;
import com.sparta.homework4.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class UploadController {

    private final S3Uploader s3Uploader;

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/api/contents/{contentId}/images")
    @ResponseBody
    public Response<String> upload(@RequestParam("images") MultipartFile multipartFile,
                         @PathVariable(name = "contentId") Long contentId,
                         @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        if (userDetails == null) {
            return Response.success("로그인이 필요합니다.");
        } else {
            return this.s3Uploader.upload(multipartFile, "static", contentId, userDetails.getUsername());
        }
    }
}