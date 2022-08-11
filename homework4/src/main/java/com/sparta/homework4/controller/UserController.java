package com.sparta.homework4.controller;

import com.sparta.homework4.dto.LoginRequestDto;
import com.sparta.homework4.dto.SignupRequestDto;
import com.sparta.homework4.jwt.JwtTokenProvider;
import com.sparta.homework4.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping({"/user/login"})
    public String login(@RequestBody LoginRequestDto loginRequestDto) {
        if (this.userService.login(loginRequestDto)) {
            String token = this.jwtTokenProvider.createToken(loginRequestDto.getUsername());
            System.out.println(token);
            return token;
        } else {
            return "닉네임 또는 패스워드를 확인해주세요";
        }
    }

    @PostMapping({"/user/signup"})
    public String registerUser(@RequestBody @Valid SignupRequestDto requestDto) {
        String res = this.userService.registerUser(requestDto);
        return res.equals("") ? "회원가입 성공" : res;
    }

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
