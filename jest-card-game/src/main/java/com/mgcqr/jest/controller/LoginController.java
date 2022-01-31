package com.mgcqr.jest.controller;

import com.mgcqr.jest.dto.LoginDto;
import com.mgcqr.jest.dto.LoginResDto;
import com.mgcqr.jest.dto.PublicKeyResDto;
import com.mgcqr.jest.dto.ResponseWrapper;
import com.mgcqr.jest.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/public-key")
    public ResponseWrapper<PublicKeyResDto> getPublicKey(){
        PublicKeyResDto publicKeyResDto = new PublicKeyResDto();
        publicKeyResDto.setPublicKey(loginService.getPublicKey());
        return new ResponseWrapper<>(publicKeyResDto);
    }

    @PutMapping
    public ResponseWrapper<LoginResDto> login(@RequestBody LoginDto loginDto){
        return loginService.checkUserLogin(loginDto);
    }
    @PostMapping("/sign-up")
    public ResponseWrapper<LoginResDto> signUp(@RequestBody LoginDto loginDto){
        return loginService.signUp(loginDto);
    }
}
