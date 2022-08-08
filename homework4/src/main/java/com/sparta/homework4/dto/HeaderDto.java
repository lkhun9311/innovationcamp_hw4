package com.sparta.homework4.dto;

public class HeaderDto {
    private String TOKEN;

    public String getTOKEN() {
        return this.TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public HeaderDto(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public HeaderDto() {
    }
}
