package com.sparta.homework4.dto;

public class LoginRequestDto {
    private String username;
    private String password;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public LoginRequestDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginRequestDto() {
    }
}
