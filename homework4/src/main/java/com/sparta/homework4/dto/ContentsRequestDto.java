package com.sparta.homework4.dto;

public class ContentsRequestDto {
    private String title;
    private String name;
    private String contents;
    private String image;

    public ContentsRequestDto() {}

    public String getTitle() {
        return this.title;
    }

    public String getName() {
        return this.name;
    }

    public String getContents() { return this.contents; }

    public String getImage() { return this.image; }
}
