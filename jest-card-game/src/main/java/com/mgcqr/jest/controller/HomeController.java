package com.mgcqr.jest.controller;

import com.mgcqr.jest.service.impl.BasicServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
//@CrossOrigin(origins = "http://127.0.0.1:5500", maxAge = 3600)
//@RequestMapping("/home")
public class HomeController {

    @Autowired
    private BasicServiceImpl basicService;


    @GetMapping("/debug")
    public String hello(){
        String res = "OK";
        try {
            basicService.debug();
        }catch (Exception e){
            res = "error";
        }
        System.out.println("debug");
        return res;
    }


}
