package com.sparta.makeboard.controller;

import com.sparta.makeboard.repository.BoardRepository;
import com.sparta.makeboard.domain.Board;
import com.sparta.makeboard.domain.BoardRequestDto;
import com.sparta.makeboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @PostMapping("/api/boards")
    public Board createboard(@RequestBody BoardRequestDto requestDto) {
        Board board = new Board(requestDto);
        return boardRepository.save(board);
    }

    @GetMapping("/api/boards")
    public List<Board> getboard() {return boardRepository.findAllByOrderByModifiedAtDesc();
    }


    @GetMapping("/api/boards/{id}")
    public String getBoard(@PathVariable Long id) {
        return boardRepository.findById(id).get().getPassword();


    }



    @PutMapping("/api/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto) {
        boardService.update(id, requestDto);
        return id;

    }



    @DeleteMapping("/api/boards/{id}")
    public Long deleteBoard(@PathVariable Long id) {
        boardRepository.deleteById(id);
        return id;
    }
}