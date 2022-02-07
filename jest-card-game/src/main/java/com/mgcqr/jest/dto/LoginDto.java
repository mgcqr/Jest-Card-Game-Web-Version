package com.mgcqr.jest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoginDto {
    private String userName;
    /**
     * RSA加密后的base64密码
     */
    private String passWord;
}
