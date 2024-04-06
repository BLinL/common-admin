package com.example.spst.account.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
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
