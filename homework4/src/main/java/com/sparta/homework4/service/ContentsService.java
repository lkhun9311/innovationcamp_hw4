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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ContentsService {
    private final ContentsRepository ContentsRepository;
    private final ReplyRepository ReplyRepository;
    private final ContentLikeRepository contentLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Contents createContents(ContentsRequestDto requestDto, String username) {
        String contentsCheck = requestDto.getContents();
        String titleCheck = requestDto.getTitle();
        Contents contents;
        if (!contentsCheck.contains("script") && !contentsCheck.contains("<") && !contentsCheck.contains(">")) {
            if (!titleCheck.contains("script") && !titleCheck.contains("<") && !titleCheck.contains(">")) {
                contents = new Contents(requestDto, username);
                this.ContentsRepository.save(contents);
                return contents;
            } else {
                contents = new Contents("xss 안돼요,,하지마세요ㅠㅠ", username, "xss 안돼요,,하지마세요ㅠㅠ");
                this.ContentsRepository.save(contents);
                return contents;
            }
        } else {
            contents = new Contents(requestDto, username, "xss 안돼요,,하지마세요ㅠㅠ");
            this.ContentsRepository.save(contents);
            return contents;
        }
    }

    public List<ContentsResponseDto> getContents() {
        List<Contents> contents = this.ContentsRepository.findAllByOrderByCreatedAtDesc();
        List<ContentsResponseDto> listContents = new ArrayList();
        Iterator var3 = contents.iterator();

        while(var3.hasNext()) {
            Contents content = (Contents)var3.next();
            int countReply = this.ReplyRepository.countByPostid(content.getId());
            ContentsResponseDto contentsResponseDto = ContentsResponseDto.builder().content(content).countReply(countReply).build();
            listContents.add(contentsResponseDto);
        }

        return listContents;
    }

    @Transactional
    public Long update(Long id, ContentsRequestDto requestDto) {
        Contents Contents = (Contents)this.ContentsRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("아이디가 존재하지 않습니다.");
        });
        Contents.update(requestDto);
        return Contents.getId();
    }

    public void deleteContent(Long ContentId, String userName) {
        String writer = ((Contents)this.ContentsRepository.findById(ContentId).orElseThrow(() -> {
            return new IllegalArgumentException("게시글이 존재하지 않습니다.");
        })).getName();
        if (Objects.equals(writer, userName)) {
            this.ContentsRepository.deleteById(ContentId);
        } else {
            new IllegalArgumentException("작성한 유저가 아닙니다.");
        }

    }

    public ContentsService(ContentsRepository ContentsRepository, ReplyRepository ReplyRepository, ContentLikeRepository contentLikeRepository, UserRepository userRepository) {
        this.ContentsRepository = ContentsRepository;
        this.ReplyRepository = ReplyRepository;
        this.contentLikeRepository = contentLikeRepository;
        this.userRepository = userRepository;
    }

    private Contents getContentsInService(Long contentId) {
        Optional<Contents> byContentId = ContentsRepository.findById(contentId);
        return byContentId.orElseThrow(() -> new ContentsNotFound("해당 게시글이 존재하지 않습니다."));
    }

    private User getUserInService(Long userId) {
        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId.orElseThrow(() -> new UsernameNotFoundException("게시글 작성 권한이 없습니다. 로그인이 필요합니다."));
        return user;
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
//            contentLikeRepository.save(contentLike);
        } else {
            contentLike.mapToContent(contents);
            contentLike.mapToUser(user);
            contents.updateLikeCount();
            contentLikeRepository.save(contentLike);
        }
    }
}
