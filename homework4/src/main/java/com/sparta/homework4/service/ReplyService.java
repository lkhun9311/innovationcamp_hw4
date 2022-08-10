package com.sparta.homework4.service;

import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.model.User;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.repository.ReplyRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.transaction.Transactional;

import com.sparta.homework4.repository.UserRepository;
import com.sparta.homework4.util.response.ContentsNotFound;
import com.sparta.homework4.util.response.Response;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;
    private final ReplyRepository ReplyRepository;

    public List<Reply> getReply(Long ContentsId) {
        return this.ReplyRepository.findAllByContentsIdOrderByCreatedAtDesc(ContentsId);
    }

    @Transactional
    public Response<String> createReply(Long contentsId, ReplyRequestDto requestDto, String username, Long userId) {
        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId.orElseThrow(() -> new UsernameNotFoundException("로그인이 필요합니다."));

        Optional<Contents> byContentsId = contentsRepository.findById(contentsId);
        Contents contents = byContentsId
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        String replyCheck = requestDto.getReply();
        Reply reply;
        if (!replyCheck.contains("script") && !replyCheck.contains("<") && !replyCheck.contains(">")) {
            reply = new Reply(requestDto, username);
            reply.mapToContentsAndUser(contents, user);
            this.ReplyRepository.save(reply);
            return Response.<String>builder().status(200).data(reply.getUserId()+"번 댓글 작성을 완료했습니다.").build();
        } else {
            reply = new Reply("xss 안돼요,, 하지마세요ㅠㅠ", username);
            this.ReplyRepository.save(reply);
            return Response.<String>builder().status(200).data("xss 안돼요,,하지마세요ㅠㅠ").build();
        }
    }

    @Transactional
    public Response<String> update(Long contentsId, ReplyRequestDto requestDto, String username, Long userId) {
        Reply reply = (Reply)this.ReplyRepository.findById(contentsId)
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));
        Long writerId = reply.getUserId();
        if (Objects.equals(writerId, userId)) {
            reply.update(requestDto);
            return Response.<String>builder().status(200).data(reply.getUserId()+"번 댓글을 수정했습니다.").build();
        } else {
            return Response.<String>builder().status(200).data(reply.getUserId()+"번 댓글의 작성자가 아닙니다.").build();
        }
    }

    public Response<String> deleteReply(Long contentsId, Long userId) {
        Reply reply = (Reply)this.ReplyRepository.findById(contentsId)
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));
        Long writerId = ((Reply)this.ReplyRepository.findById(contentsId).orElseThrow(() -> {
            return new IllegalArgumentException("해당 게시글이 삭제되었거나 존재하지 않습니다.");
        })).getUserId();
        if (Objects.equals(writerId, userId)) {
            this.ReplyRepository.deleteById(contentsId);
            return Response.<String>builder().status(200).data(reply.getUserId()+"번 댓글을 삭제했습니다.").build();
        } else {
            return Response.<String>builder().status(200).data(reply.getUserId()+"번 댓글의 작성자가 아닙니다.").build();
        }
    }

    public ReplyService(UserRepository userRepository,
                        ContentsRepository contentsRepository,
                        ReplyRepository ReplyRepository) {
        this.userRepository = userRepository;
        this.contentsRepository = contentsRepository;
        this.ReplyRepository = ReplyRepository;
    }
}
