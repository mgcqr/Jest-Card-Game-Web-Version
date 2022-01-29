package com.mgcqr.jest.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String userName;
    private  String passWord;
    private Boolean isLoggingIn;
}
