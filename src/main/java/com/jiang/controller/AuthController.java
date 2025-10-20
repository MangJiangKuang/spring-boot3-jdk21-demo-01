package com.jiang.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("userName", "");
        String password = body.getOrDefault("password", "");

        Subject subject = SecurityUtils.getSubject();
        System.out.println("第一步username: " + username);
        try {
            // 直接使用原始密码，让 Shiro 自动处理加盐验证
            subject.login(new UsernamePasswordToken(username, password));
            return "OK 登录成功:"+username;
        } catch (AuthenticationException e) {
            return "LOGIN_FAILED: " + e.getMessage();
        }
    }

    @PostMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "LOGOUT_OK";
    }
}


