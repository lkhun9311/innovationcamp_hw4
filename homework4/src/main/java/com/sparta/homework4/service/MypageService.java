package com.sparta.homework4.service;

import com.sparta.homework4.model.Contents;
import com.sparta.homework4.model.Reply;
import com.sparta.homework4.repository.ContentsRepository;
import com.sparta.homework4.repository.ReplyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MypageService {

    private final ReplyRepository ReplyRepository;
    private final ContentsRepository contentsRepository;


    public MypageService(ReplyRepository ReplyRepository, ContentsRepository contentsRepository) {
        this.ReplyRepository = ReplyRepository;
        this.contentsRepository = contentsRepository;
    }

    public List<Reply> getMypageReply(String username) {
        return this.ReplyRepository.findAllByUsernameOrderByCreatedAtDesc(username);
    }

    public List<Contents> getMypageContents(Long userId) {
        return this.contentsRepository.findAllByUserIdOrderByCreatedAtDesc(userId);
    }

}
