package com.eccomrce.eccomrce.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/de")
public class Default {

    @GetMapping("/get")
    public String getMethodName(@RequestParam(value = "param", required = false, defaultValue = "default") String param) {
        return "hello dear, received param: " ;
    }
}
