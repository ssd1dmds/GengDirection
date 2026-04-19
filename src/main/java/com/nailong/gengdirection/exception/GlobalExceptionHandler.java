package com.nailong.gengdirection.exception;

import com.nailong.gengdirection.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
    全局异常Handler
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GengException.class)
    public Result<Void> handleGengException(GengException e){
        log.warn("GengException:{}", e.getMessage());
        return Result.error(e.getStatusCode(), e.getMessage());

    }
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e){
        log.warn("Exception(From java.lang):{}", e.getMessage());
        return Result.error(500, "Internal Server Error");

    }
}
