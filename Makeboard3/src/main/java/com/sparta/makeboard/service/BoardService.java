package com.sparta.makeboard.service;

import com.sparta.makeboard.domain.Board;
import com.sparta.makeboard.repository.BoardRepository;
import com.sparta.makeboard.domain.BoardRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long update(Long id, BoardRequestDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        board.update(requestDto);
        return id;
    }
}
