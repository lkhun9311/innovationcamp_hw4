package com.sparta.homework4.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private int status;
    private T data;

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .status(200)
                .data(data)
                .build();
    }
}
