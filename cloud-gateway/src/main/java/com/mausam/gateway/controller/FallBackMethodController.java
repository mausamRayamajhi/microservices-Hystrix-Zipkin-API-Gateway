package com.mausam.gateway.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallBackMethodController {

    @GetMapping("userServiceFallBack")
    public String userServiceFallBackMethod(){
        return "User Service is taking longer time than Expected." +
                " Please try Again later";
    }

    @GetMapping("departmentServiceFallBack")
    public String departmentServiceFallBackMethod(){
        return "Department Service is taking longer time than Expected." +
                " Please try Again later";
    }
}
