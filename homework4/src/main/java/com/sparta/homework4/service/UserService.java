package com.sparta.homework4.service;

import com.sparta.homework4.dto.LoginRequestDto;
import com.sparta.homework4.dto.SignupRequestDto;
import com.sparta.homework4.jwt.JwtTokenProvider;
import com.sparta.homework4.model.User;
import com.sparta.homework4.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public Boolean login(LoginRequestDto loginRequestDto) {
        User user = (User)this.userRepository.findByUsername(loginRequestDto.getUsername()).orElse((User) null);
        if (user != null) {
            return !this.passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword()) ? false : true;
        } else {
            return false;
        }
    }

    public String registerUser(SignupRequestDto requestDto) {
        String error = "";
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();
        String password2 = requestDto.getPassword2();
        String pattern = "^[a-zA-Z0-9]*$";
        Optional<User> found = this.userRepository.findByUsername(username);
        if (found.isPresent()) {
            return "중복된 id 입니다.";
        } else if (username.length() < 3) {
            return "닉네임을 3자 이상 입력하세요";
        } else if (!Pattern.matches(pattern, username)) {
            return "알파벳 대소문자와 숫자로만 입력하세요";
        } else if (!password.equals(password2)) {
            return "비밀번호가 일치하지 않습니다";
        } else if (password.length() < 4) {
            return "비밀번호를 4자 이상 입력하세요";
        } else if (password.contains(username)) {
            return "비밀번호에 닉네임을 포함할 수 없습니다.";
        } else {
            password = this.passwordEncoder.encode(password);
            requestDto.setPassword(password);
            User user = new User(username, password);
            this.userRepository.save(user);
            return error;
        }
    }

    public UserService(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }
}
