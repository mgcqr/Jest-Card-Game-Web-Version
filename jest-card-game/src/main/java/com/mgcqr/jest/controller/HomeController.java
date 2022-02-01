package com.mgcqr.jest.controller;

import com.mgcqr.jest.service.BasicService;
import com.mgcqr.jest.interceptor.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        res.put("user", UserContextHolder.get());
        return res;
        //try
    }


}
