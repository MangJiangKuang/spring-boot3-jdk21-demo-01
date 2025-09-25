package com.jiang.service;

import com.jiang.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    //处理登录的方法
    String getUserInfo(User user);

    //查找用户是否存在
    User getUserByUserName(String username);
    //注册新用户
    String register(User user);

    String updateUser(User user);

    String deleteUser(User user);
}
