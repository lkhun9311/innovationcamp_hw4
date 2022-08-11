package com.sparta.homework4.service;

import com.sparta.homework4.dto.ContentsRequestDto;
import com.sparta.homework4.dto.ContentsResponseDto;
import com.sparta.homework4.model.ContentLike;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.User;
import com.sparta.homework4.repository.ContentLikeRepository;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.repository.ReplyRepository;
import com.sparta.homework4.repository.UserRepository;
import com.sparta.homework4.util.response.ContentsNotFound;
import com.sparta.homework4.util.response.Response;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ContentsService {
    private final ContentsRepository contentsRepository;
    private final ReplyRepository replyRepository;
    private final ContentLikeRepository contentLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Response<String> createContents(ContentsRequestDto requestDto, String username, Long userId) {
        User user = getUserInService(userId);

        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        Contents contents;
        if (!contentsCheck.contains("script") && !contentsCheck.contains("<") && !contentsCheck.contains(">")) {
            if (!titleCheck.contains("script") && !titleCheck.contains("<") && !titleCheck.contains(">")) {
                contents = new Contents(requestDto, username);
                contents.mapToUser(user);
                this.contentsRepository.save(contents);
                return Response.<String>builder().status(200).data("게시글 작성을 완료했습니다.").build();
            } else {
                contents = new Contents("xss 안돼요,,하지마세요ㅠㅠ", username, "xss 안돼요,,하지마세요ㅠㅠ");
                contents.mapToUser(user);
                this.contentsRepository.save(contents);
                return Response.<String>builder().status(200).data("xss 안돼요,,하지마세요ㅠㅠ").build();
            }
        } else {
            contents = new Contents(requestDto, username, "xss 안돼요,,하지마세요ㅠㅠ");
            contents.mapToUser(user);
            this.contentsRepository.save(contents);
            return Response.<String>builder().status(200).data("xss 안돼요,,하지마세요ㅠㅠ").build();
        }
    }

    @Transactional
    public List<ContentsResponseDto> getContents() {
        List<Contents> contents = contentsRepository.findAllByOrderByCreatedAtDesc();
        List<ContentsResponseDto> listContents = new ArrayList<>();
        for (Contents content : contents) {
            Long countReply = replyRepository.countByContentsId(content.getId());
            ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder()
                    .content(content)
                    .countReply(countReply)
                    .build();
            listContents.add(contentsResponseDto);
        }
        return listContents;
    }

    @Transactional
    public Response<String> updateContent(Long ContentId, ContentsRequestDto requestDto, String userName) {
        Contents content = this.contentsRepository.findById(ContentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (Objects.equals(content.getName(), userName)) {
            content.update(requestDto);
            return Response.<String>builder().status(200).data("게시글을 수정했습니다.").build();
        } else {
            return Response.<String>builder().status(200).data("게시글의 작성자가 아닙니다.").build();
        }
    }

    public Response<String> deleteContent(Long ContentId, String userName) {
        Contents content = this.contentsRepository.findById(ContentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (Objects.equals(content.getName(), userName)) {
            this.contentsRepository.deleteById(ContentId);
            return Response.<String>builder().status(200).data("게시글을 삭제했습니다.").build();
        } else {
            return Response.<String>builder().status(200).data("게시글의 작성자가 아닙니다.").build();
        }
    }

    private User getUserInService(Long userId) {
        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId.orElseThrow(() -> new UsernameNotFoundException("로그인이 필요합니다."));
        return user;
    }

    private Contents getContentsInService(Long contentId) {
        Optional<Contents> byContentId = contentsRepository.findById(contentId);
        return byContentId.orElseThrow(() -> new ContentsNotFound("해당 게시글이 존재하지 않습니다."));
    }

    @Transactional
    public void contentLike(Long contentId, Long userId) {
        Contents contents = getContentsInService(contentId);
        User user = getUserInService(userId);
        Optional<ContentLike> byContentsAndUser = contentLikeRepository.findByContentsAndUser(contents, user);

        ContentLike contentLike = ContentLike.builder().build();

        if(byContentsAndUser.isPresent()){
            contents.discountLike(byContentsAndUser.get());
            contents.updateLikeCount();
            contentLikeRepository.delete(byContentsAndUser.get());
        } else {
            contentLike.mapToContent(contents);
            contentLike.mapToUser(user);
            contents.updateLikeCount();
            contentLikeRepository.save(contentLike);
        }
    }

    public ContentsService(ContentsRepository contentsRepository,
                           ReplyRepository replyRepository,
                           ContentLikeRepository contentLikeRepository,
                           UserRepository userRepository) {
        this.contentsRepository = contentsRepository;
        this.replyRepository = replyRepository;
        this.contentLikeRepository = contentLikeRepository;
        this.userRepository = userRepository;
    }
}
