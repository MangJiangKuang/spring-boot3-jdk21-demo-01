package com.jiang.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PingController {

    @GetMapping("/public/ping")
    public String publicPing() {
        return "public pong +"+ SecurityUtils.getSubject();
    }

    @RequiresAuthentication
    @GetMapping("/private/ping")
    public String privatePing() {
        return "private pong";
    }
}
