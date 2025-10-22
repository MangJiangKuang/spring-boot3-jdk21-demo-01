package com.jiang.controller;

import com.jiang.entity.Menu;
import com.jiang.entity.Role;
import com.jiang.entity.User;
import com.jiang.service.UserService;
import com.jiang.utils.response.ResponseContext;
import jakarta.annotation.Resource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Resource
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> body) {
        String username = body.getOrDefault("userName", "");
        String password = body.getOrDefault("password", "");

        Subject subject = SecurityUtils.getSubject();
        try {
            // 直接使用原始密码，让 Shiro 自动处理加盐验证
            subject.login(new UsernamePasswordToken(username, password));
            //返回权限和身份信息
            return "OK 登录成功:"+username+"subject:"+subject;
        } catch (AuthenticationException e) {
            return "LOGIN_FAILED: " + e.getMessage();
        }
    }
    //返回权限和身份信息
    @PostMapping("/getInfo")
    public String getInfo() {
        return "OK 获取成功:"+SecurityUtils.getSubject().getPrincipal();
    }
    //获取菜单
    @PostMapping("/getMenu")
    public ResponseContext< Map<Role, Set<Menu>>> getMenu() {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        User user = userService.getUserByUserName(username);
        /*// 方法1：检查具体权限
        if (subject.isPermitted("menu:view")) {
            return ;
        }*/


        Map<Role, Set<Menu>> roleMenus = new java.util.HashMap<>(Map.of());
        user.getRoles().forEach(role -> {
            //角色名与菜单对应
            roleMenus.put(role,role.getMenus());
        });
        return ResponseContext.success(roleMenus);
    }
    @PostMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "LOGOUT_OK";
    }
}


