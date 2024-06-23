package com.example.spst.account.web;

import com.example.spst.account.service.Myservice;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @Autowired
    Myservice myservice;

    @RequestMapping("/hello")
    public String hello() {
        return myservice.getName();
    }

    @RequestMapping("/p1")
    public String p1() {
        return "p1";
    }

    @PreAuthorize("hasRole('USER')")
    @RequestMapping("/p2")
    public String p2() {
        return "p2";
    }

}
