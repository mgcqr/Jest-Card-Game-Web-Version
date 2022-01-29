package com.mgcqr.controller;

import com.mgcqr.dto.LoginDto;
import com.mgcqr.dto.LoginResDto;
import com.mgcqr.service.BasicService;
import com.mgcqr.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", maxAge = 3600)
//@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BasicService basicService;

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public Map<String, Object> hello(){
        Map<String, Object> res = new HashMap<>();
        res.put("word", "hello-world");
        res.put("count", basicService.count());
        return res;
        //try
    }

    @PostMapping("/login")
    public LoginResDto login(@RequestBody LoginDto loginDto,
                             HttpServletRequest request){
        System.out.println("logging in");
        System.out.println(loginDto);

        LoginResDto res = new LoginResDto();
        String token = null;
        if(loginDto.getPassWord().equals("123456")) {
            token = "FF-FF-FF";
            request.getSession().setAttribute("token", token);
        }
        res.setToken(token);
        return res;
    }
    @GetMapping("/sign-up")
    public Map<String, Object> signUp(){
        Map<String, Object> res = new HashMap<>();
        res.put("word", "hello-world");
        res.put("count", basicService.count());
        return res;
        //try
    }
    @GetMapping("/test")
    public Map<String, Object> test(){
        Map<String, Object> res = new HashMap<>();
        res.put("message", "test");
        userService.test();
        return res;
        //try
    }
}
