package com.jiang.service.impl;

import com.jiang.entity.Menu;
import com.jiang.entity.User;
import com.jiang.repository.MenuRepository;
import com.jiang.service.MenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MenuServiceImpl implements MenuService {
    @Resource
    MenuRepository menuRepository;

    @Override
    public String addMenu(List<Menu> menu) {
        menuRepository.saveAll(menu);
        return "添加菜单成功";
    }

    @Override
    public String deleteMenu(List<Long> menuIds) {
        menuRepository.deleteAllById(menuIds);
        return "删除菜单成功";
    }
}
