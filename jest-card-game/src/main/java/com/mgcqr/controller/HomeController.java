package com.mgcqr.controller;

import com.mgcqr.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BasicService basicService;

    @GetMapping("/hello")
    public Map<String, Object> hello(){
        Map<String, Object> res = new HashMap<>();
        res.put("word", "hello-world");
        res.put("count", basicService.count());
        return res;
    }
}
