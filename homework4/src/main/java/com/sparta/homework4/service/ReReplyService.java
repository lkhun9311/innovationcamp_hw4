package com.sparta.homework4.service;

import com.sparta.homework4.dto.ReReplyRequestDto;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.ReReply;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.model.User;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.repository.ReReplyRepository;
import com.sparta.homework4.repository.ReplyRepository;
import com.sparta.homework4.repository.UserRepository;
import com.sparta.homework4.util.response.ContentsNotFound;
import com.sparta.homework4.util.response.ReReplyNotFound;
import com.sparta.homework4.util.response.ReplyNotFound;
import com.sparta.homework4.util.response.Response;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReReplyService {
    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;
    private final ReplyRepository replyRepository;
    private final ReReplyRepository reReplyRepository;

    public List<ReReply> getReReply(Long replyId, Long contentsId){
        return this.reReplyRepository.findAllByReplyIdAndContentsIdOrderByCreatedAtDesc(replyId, contentsId);
    }

    @Transactional
    public Response<String> createReReply(ReReplyRequestDto requestDto, Long contentsId, Long replyId, String username, Long userId){
        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId.orElseThrow(() -> new UsernameNotFoundException("로그인이 필요합니다."));

        Optional<Contents> byContentsId = contentsRepository.findById(contentsId);
        Contents contents = byContentsId.orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Optional<Reply> byReplyId = replyRepository.findById(replyId);
        Reply reply = byReplyId.orElseThrow(() -> new ContentsNotFound("해당 댓글이 삭제되었거나 존재하지 않습니다."));

        String reReplyCheck = requestDto.getReReply();
        ReReply reReply;
        if(!reReplyCheck.contains("script") && !reReplyCheck.contains("<") && !reReplyCheck.contains(">")){
            reReply = new ReReply(requestDto, username);
            reReply.mapToUser(user);
            reReply.mapToContents(contents);
            reReply.mapToReply(reply);
            reply.updateReReplyCount();
            contents.updateReReplyCount();
            this.reReplyRepository.save(reReply);

            return Response.<String>builder().status(200).data("대댓글을 작성했습니다.").build();
        } else{
            reReply = new ReReply("xss 안돼요,, 하지마세요ㅠㅠ", username);
            return Response.<String>builder().status(200).data("xss 안돼요,,하지마세요ㅠㅠ").build();
        }
    }

    @Transactional
    public String update(Long rereplyId, ReReplyRequestDto requestDto, Long userId, Long contentsId, Long replyId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Contents contents = contentsRepository.findById(contentsId)
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFound("해당 댓글이 삭제되었거나 존재하지 않습니다."));

        ReReply reReply = reReplyRepository.findById(rereplyId)
                .orElseThrow(() -> new ReReplyNotFound("해당 대댓글이 삭제되었거나 존재하지 않습니다."));

        Long writerId = reReply.getUserId();

        if (Objects.equals(writerId, userId)) {
            reReply.update(requestDto);
            return "대댓글을 수정했습니다.";
        } else {
            return "대댓글의 작성자가 아닙니다.";
        }
    }

    @Transactional
    public String deleteReply(Long rereplyId, Long replyId, Long contentsId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Contents contents = contentsRepository.findById(contentsId)
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFound("해당 댓글이 삭제되었거나 존재하지 않습니다."));

        ReReply reReply = reReplyRepository.findById(rereplyId)
                .orElseThrow(() -> new ReReplyNotFound("해당 대댓글이 삭제되었거나 존재하지 않습니다."));

        Long writerId = reReply.getUserId();

        if (Objects.equals(writerId, userId)) {
            this.reReplyRepository.deleteById(rereplyId);
            reReply.mapToUserRemove(user);
            reReply.mapToContentsRemove(contents);
            reReply.mapToReplyRemove(reply);
            reply.updateReReplyCount();
            contents.updateReReplyCount();
            return "대댓글을 삭제했습니다.";
        } else {
            return "대댓글의 작성자가 아닙니다.";
        }
    }

    public ReReplyService(UserRepository userRepository,
                          ContentsRepository contentsRepository,
                          ReplyRepository replyRepository,
                          ReReplyRepository reReplyRepository) {
        this.userRepository = userRepository;
        this.contentsRepository = contentsRepository;
        this.replyRepository = replyRepository;
        this.reReplyRepository = reReplyRepository;
    }
}
