package com.jw.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Result<T> {

    private String resultCode;
    private String resultMsg;
    private T payload;

    public Result(String code, String msg, T payload){
        this.resultCode = code;
        this.resultMsg = msg;
        this.payload = payload;
    }
    public Result(){}
}
