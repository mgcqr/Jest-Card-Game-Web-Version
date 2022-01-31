package com.mgcqr.jest.service;

import com.mgcqr.jest.dto.LoginDto;
import com.mgcqr.jest.dto.LoginResDto;
import com.mgcqr.jest.dto.ResponseWrapper;

public interface LoginService {
    String getPublicKey();

    ResponseWrapper<LoginResDto> checkUserLogin(LoginDto loginDto);

    ResponseWrapper<LoginResDto> signUp(LoginDto loginDto);
}
