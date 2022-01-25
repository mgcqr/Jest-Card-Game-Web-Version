package com.mgcqr.controller;

import com.mgcqr.dto.LoginDto;
import com.mgcqr.service.BasicService;
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

    @GetMapping("/hello")
    public Map<String, Object> hello(){
        Map<String, Object> res = new HashMap<>();
        res.put("word", "hello-world");
        res.put("count", basicService.count());
        return res;
        //try
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginDto loginDto,
                                     HttpServletRequest request){
        System.out.println("logging in");
        System.out.println(loginDto.getUserName());
        System.out.println(loginDto.getPassWord());

        Map<String, Object> res = new HashMap<>();
        String token = null;
        if(loginDto.getPassWord().equals("123456")) {
            token = "FF-FF-FF";
            request.getSession().setAttribute("token", token);
        }
        res.put("token", token);
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
}
