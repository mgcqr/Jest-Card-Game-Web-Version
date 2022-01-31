package com.mgcqr.jest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginDto {
    //@JsonProperty("un")
    //入参也可以用
    private String userName;
    /**
     * RSA加密后的base64密码
     */
    private String passWord;
}
