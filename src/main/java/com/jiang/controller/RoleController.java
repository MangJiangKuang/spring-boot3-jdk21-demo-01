package com.jiang.controller;

import com.jiang.entity.Role;
import com.jiang.service.RoleService;
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
@RequestMapping("/Role")// 这个注解组合了@ResponseBody和@Controller注解的功能 标识这是一个控制器类，用于处理 HTTP 请求 自动将方法返回值序列化为 JSON/XML 响应体 适用于构建 RESTful API
public class RoleController {
    @Resource // 自动注入 UserService 实例
    RoleService roleService;
    //注册新角色
    @Operation(summary = "注册新角色", description = "注册新角色")
    @PostMapping("/register")
    public String register(@RequestBody Role role) {
        return  roleService.addRole(role);
    }
    //删除角色
    @Operation(summary = "删除角色", description = "删除角色")
    @PostMapping("/deleteRole")
    public String deleteRole(@RequestBody Role role) {
        return  roleService.deleteRole(role);
    }
}
