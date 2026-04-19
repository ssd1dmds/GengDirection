package com.nailong.gengdirection.exception;

import lombok.Getter;

@Getter
public class GengException extends RuntimeException{
    private final Integer StatusCode;
    public GengException(String Message) {
        super(Message);
        this.StatusCode = 400;
    }
    public GengException(Integer StatusCode, String message) {
        super(message);

        this.StatusCode = StatusCode;

    }
}
