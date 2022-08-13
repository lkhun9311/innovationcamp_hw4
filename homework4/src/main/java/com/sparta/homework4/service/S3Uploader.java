
package com.sparta.homework4.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.util.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final ContentsRepository contentsRepository;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    // MultipartFile을 전달받아 File로 전환한 후 S3에 업로드
    public Response<String> upload(MultipartFile multipartFile, String dirName, Long contentId, String userName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("파일 변환에 실패했습니다"));

        Contents contents = this.contentsRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        if (Objects.equals(contents.getName(), userName)) {
            return upload(uploadFile, dirName);
        } else {
            return Response.<String>builder().status(200).data("게시글의 작성자가 아닙니다.").build();
        }
    }

    private Response<String> upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        return Response.<String>builder().status(200).data(uploadImageUrl).build();
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.Private));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    public S3Uploader(AmazonS3Client amazonS3Client,
                      ContentsRepository contentsRepository) {
        this.amazonS3Client = amazonS3Client;
        this.contentsRepository = contentsRepository; }
}