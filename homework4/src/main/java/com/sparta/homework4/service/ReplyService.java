package com.sparta.homework4.service;

import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.repository.ReplyRepository;
import java.util.List;
import java.util.Objects;
import javax.transaction.Transactional;

import com.sparta.homework4.util.response.Response;
import org.springframework.stereotype.Service;

@Service
public class ReplyService {
    private final ReplyRepository ReplyRepository;

    public List<Reply> getReply(Long postId) {
        return this.ReplyRepository.findAllByPostidOrderByCreatedAtDesc(postId);
    }

    @Transactional
    public Response<String> createReply(ReplyRequestDto requestDto, String username, Long userId) {
        String replyCheck = requestDto.getReply();
        Reply reply;
        if (!replyCheck.contains("script") && !replyCheck.contains("<") && !replyCheck.contains(">")) {
            reply = new Reply(requestDto, username, userId);
            this.ReplyRepository.save(reply);
            return Response.<String>builder().status(200).data("댓글 작성을 완료했습니다.").build();
        } else {
            reply = new Reply(requestDto, username, userId, "xss 안돼요,, 하지마세요ㅠㅠ");
            this.ReplyRepository.save(reply);
            return Response.<String>builder().status(200).data("xss 안돼요,,하지마세요ㅠㅠ").build();
        }
    }

    @Transactional
    public String update(Long id, ReplyRequestDto requestDto, String username, Long userId) {
        Reply reply = (Reply)this.ReplyRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않습니다.");
        });
        Long writerId = reply.getUserId();
        if (Objects.equals(writerId, userId)) {
            reply.update(requestDto);
            return "댓글 수정 완료";
        } else {
            return "작성한 유저가 아닙니다.";
        }
    }

    public String deleteReply(Long replyId, Long userId) {
        Long writerId = ((Reply)this.ReplyRepository.findById(replyId).orElseThrow(() -> {
            return new IllegalArgumentException("게시글이 존재하지 않습니다.");
        })).getUserId();
        if (Objects.equals(writerId, userId)) {
            this.ReplyRepository.deleteById(replyId);
            return "댓글 삭제 완료";
        } else {
            return "작성한 유저가 아닙니다.";
        }
    }

    public ReplyService(ReplyRepository ReplyRepository) {
        this.ReplyRepository = ReplyRepository;
    }
}
