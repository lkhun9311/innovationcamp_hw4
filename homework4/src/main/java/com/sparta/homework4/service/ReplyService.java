package com.sparta.homework4.service;

import com.sparta.homework4.dto.ReplyRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReplyService {
    private final ReplyRepository ReplyRepository;
    private final ReplyLikeRepository replyLikeRepository;

    public List<Reply> getReply(Long postId) {
        return this.ReplyRepository.findAllByPostidOrderByCreatedAtDesc(postId);
    }

    @Transactional

    public Response<String> createReply(Long contentsId, ReplyRequestDto requestDto, String username, Long userId) {
        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId
                .orElseThrow(() -> new UsernameNotFoundException("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Optional<Contents> byContentsId = contentsRepository.findById(contentsId);
        Contents contents = byContentsId
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        String replyCheck = requestDto.getReply();
        Reply reply;

        if (!replyCheck.contains("script") && !replyCheck.contains("<") && !replyCheck.contains(">")) {

            reply = new Reply(requestDto, username);
            reply.mapToUser(user);
            reply.mapToContents(contents);
            contents.updateReplyCount();
            this.ReplyRepository.save(reply);
            return Response.<String>builder().status(200).data("댓글 작성을 완료했습니다.").build();
        } else {
            reply = new Reply(requestDto, username, userId, "xss 안돼요,, 하지마세요ㅠㅠ");
            this.ReplyRepository.save(reply);
            return reply;
        }
    }

    @Transactional
    public Response<String> update(Long contentsId, ReplyRequestDto requestDto, Long userId, Long replyId) {
        Contents contents = this.contentsRepository.findById(contentsId)
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Reply reply = this.ReplyRepository.findById(replyId)
                .orElseThrow(() -> new ReplyNotFound("해당 댓글이 삭제되었거나 존재하지 않습니다."));

        Long writerId = reply.getUserId();

        if (Objects.equals(writerId, userId)) {
            reply.update(requestDto);

            return Response.<String>builder().status(200).data("댓글을 수정했습니다.").build();
        } else {
            return Response.<String>builder().status(200).data("댓글의 작성자가 아닙니다.").build();
        }
    }

    @Transactional
    public Response<String> deleteReply(Long contentsId, Long replyId, Long userId) {
        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId
                .orElseThrow(() -> new UsernameNotFoundException("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Optional<Contents> byContentsId = contentsRepository.findById(contentsId);
        Contents contents = byContentsId
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Optional<Reply> byReplyId = this.ReplyRepository.findById(replyId);
        Reply reply = byReplyId
                .orElseThrow(() -> new ReplyNotFound("해당 댓글이 삭제되었거나 존재하지 않습니다."));
        Long writerId = byReplyId.get().getUserId();

        if (Objects.equals(writerId, userId)) {
            ReplyRepository.deleteById(replyId);
            reply.mapToUserRemove(user);
            reply.mapToContentsRemove(contents);
            contents.updateReplyCount();
            return Response.<String>builder().status(200).data("댓글을 삭제했습니다.").build();
        } else {
            return Response.<String>builder().status(200).data("댓글의 작성자가 아닙니다.").build();
        }
    }

    @Transactional
    public void replyLike(Long contentId, Long userId, Long replyId) {
        Optional<Contents> byContentsId = contentsRepository.findById(contentId);
        Contents contents = byContentsId
                .orElseThrow(() -> new ContentsNotFound("해당 게시글이 삭제되었거나 존재하지 않습니다."));

        Optional<User> byUserId = userRepository.findById(userId);
        User user = byUserId
                .orElseThrow(() -> new UsernameNotFoundException("로그인이 필요합니다."));

        Optional<Reply> byReplyId = this.ReplyRepository.findById(replyId);
        Reply reply = byReplyId
                .orElseThrow(() -> new ReplyNotFound("해당 댓글이 삭제되었거나 존재하지 않습니다."));

        Optional<ReplyLike> byReplyAndContentsAndUser = replyLikeRepository.findByReplyAndContentsAndUser(reply, contents, user);

        ReplyLike replyLike = ReplyLike.builder().build();

        if(byReplyAndContentsAndUser.isPresent()){
            reply.discountLike(byReplyAndContentsAndUser.get());
            contents.discountReplyLike(byReplyAndContentsAndUser.get());
            user.discountReplyLike(byReplyAndContentsAndUser.get());
            reply.updateLikeCount();
            contents.updateReplyLikeCount();
            replyLikeRepository.delete(byReplyAndContentsAndUser.get());
        } else {
            replyLike.mapToReply(reply);
            replyLike.mapToContent(contents);
            replyLike.mapToUser(user);
            reply.updateLikeCount();
            contents.updateReplyLikeCount();
            replyLikeRepository.save(replyLike);
        }
    }

    public ReplyService(UserRepository userRepository,
                        ContentsRepository contentsRepository,
                        ReplyRepository ReplyRepository,
                        ReplyLikeRepository replyLikeRepository) {
        this.userRepository = userRepository;
        this.contentsRepository = contentsRepository;
        this.ReplyRepository = ReplyRepository;
        this.replyLikeRepository = replyLikeRepository;
    }
}
