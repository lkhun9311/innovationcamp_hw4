package com.sparta.homework4.service;

import com.sparta.homework4.dto.ReReplyRequestDto;
import com.sparta.homework4.dto.ReplyRequestDto;
import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.ReReply;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.model.User;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.repository.ReReplyRepository;
import com.sparta.homework4.repository.ReplyRepository;
import com.sparta.homework4.repository.UserRepository;
import com.sparta.homework4.util.response.ContentsNotFound;
import com.sparta.homework4.util.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReReplyService {
    private final UserRepository userRepository;
    private final ContentsRepository contentsRepository;
    private final ReplyRepository replyRepository;
    private final ReReplyRepository reReplyRepository;

    public List<ReReply> getReReply(Long replyId){
        return this.reReplyRepository.findAllByReplyIdOrderByCreatedAtDesc(replyId);
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
            reReply.mapToReplyAndContentsAndUser(reply, contents, user);
            this.reReplyRepository.save(reReply);

            return Response.<String>builder().status(200).data("댓글 작성을 완료했습니다.").build();
        } else{
            reReply = new ReReply("xss 안돼요,, 하지마세요ㅠㅠ", username);
            return Response.<String>builder().status(200).data("xss 안돼요,,하지마세요ㅠㅠ").build();
        }
    }

    @Transactional
    public String update(Long id, ReReplyRequestDto requestDto, String username, Long userId) {
        ReReply reReply = (ReReply)this.reReplyRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("존재하지 않습니다.");
        });

        Long writerId = reReply.getUser().getId();
        if (Objects.equals(writerId, userId)) {
            reReply.update(requestDto);
            return "댓글 수정 완료";
        } else {
            return "작성한 유저가 아닙니다.";
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
