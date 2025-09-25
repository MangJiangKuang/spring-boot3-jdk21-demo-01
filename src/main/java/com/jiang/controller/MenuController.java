package com.jiang.controller;

import com.jiang.entity.Menu;
import com.jiang.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Resource
    MenuService menuService;
    //添加菜单
    @Operation(summary = "添加菜单", description = "添加菜单")
    @PostMapping("/addMenu")
    public String addMenu(List<Menu> menu) {
        return menuService.addMenu(menu);
    }

}
