package com.sparta.homework4.dto;

public class SignupRequestDto {
    private String username;
    private String password;
    private String password2;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPassword2() {
        return this.password2;
    }

    public SignupRequestDto(String username, String password, String password2) {
        this.username = username;
        this.password = password;
        this.password2 = password2;
    }

    public SignupRequestDto() {
    }
}
