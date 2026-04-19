package com.nailong.gengdirection.common;

import lombok.Data;
/*
    全局返回结构体
 */
@Data
public class Result<T> {
    private Integer StatusCode;
    private T Data;
    private String Message;

    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>();
        result.setStatusCode(200);
        result.setData(data);
        result.setMessage("Success!");
        return result;
    }

    public static <T> Result<T> success(){
        return success(null);
    }

    public static <T> Result<T> error(Integer StatusCode, String message){
        Result<T> result = new Result<>();
        result.setStatusCode(StatusCode);
        result.setMessage(message);
        return result;
    }

}
