package com.jiang.service;

import com.jiang.entity.Menu;

import java.util.List;

public interface MenuService {
    //添加菜单
    String addMenu(List<Menu> menuList);
    //删除菜单
    String deleteMenu(List<Long> menuId);
}
