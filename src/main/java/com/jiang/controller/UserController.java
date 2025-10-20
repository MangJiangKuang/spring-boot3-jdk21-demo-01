package com.jiang.controller;

import com.jiang.entity.User;
import com.jiang.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * 处理用户相关的请求，如登录、注册、个人信息展示等
 * @author jiang
 */
@RestController
@RequestMapping("/user")// 这个注解组合了@ResponseBody和@Controller注解的功能 标识这是一个控制器类，用于处理 HTTP 请求 自动将方法返回值序列化为 JSON/XML 响应体 适用于构建 RESTful API
public class UserController {
    @Resource // 自动注入 UserService 实例
    UserService userService;
    @Operation(summary = "用户登录", description = "用户登录")
    @PostMapping("/login") // 是访问/user接口上的数据。并返回字符串
    public String login(@RequestBody User user) {
        return userService.getUserInfo(user);

    }

    //更新用户信息
    @Operation(summary = "更新用户信息", description = "更新用户信息")
    @PostMapping("/update")
    public String updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    //注册新用户
    @Operation(summary = "注册新用户", description = "注册新用户")
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        System.out.println(user.toString()+user.getRoles().toString());
        return  userService.register(user);
    }
    //删除用户
    @Operation(summary = "删除用户", description = "删除用户")
    @PostMapping("/deleteUser")
    public String deleteUser(@RequestBody User user) {
        return  userService.deleteUser(user);
    }
}
