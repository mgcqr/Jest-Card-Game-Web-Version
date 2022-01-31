package com.mgcqr.jest.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class ResponseWrapper<T> {

    private final String message;

    private final T payload;

    public ResponseWrapper(T payload){
        this.message = "ok";
        this.payload = payload;
    }
    public ResponseWrapper(String message, T payload){
        this.message = message;
        this.payload = payload;
    }
}
