package com.jiang.service.impl;

import com.jiang.entity.Role;
import com.jiang.entity.User;
import com.jiang.repository.UserRepository;
import com.jiang.service.UserService;
import com.jiang.utils.MD5Util;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserRepository userRepository;

    @Override
    public String getUserInfo(User user) {
        // 处理登录逻辑
        if (user == null || user.getUserName() == null || user.getUserName().trim().isEmpty()) {
            return "用户名不能为空";
        }
        //将用户名去掉前后空格
        user.setUserName(user.getUserName().trim());
        if(user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            return "密码不能为空";
        }
        //先通过用户名查找用户
        User dbUser = getUserByUserName(user.getUserName().trim());
        // 如果用户不存在，返回 false
        if (dbUser == null) {
            return "用户不存在";
        }
        // 对密码进行 MD5 加密
        String encryptedPassword = MD5Util.encrypt(user.getPassword()+dbUser.getSalt());
        user.setPassword(encryptedPassword);
        // 这里应该与数据库中存储的加密密码进行比较
        if (encryptedPassword.equals(dbUser.getPassword())) {
            return "登录成功，欢迎 " + user.getUserName() + "！";
        }
        return "登录失败，请检查用户名或密码。";
    }

    @Override
    public User getUserByUserName(String userName) {

        return userRepository.findByUserName(userName).orElse(null);
    }

    @Override
    public String register(User user) {

        if (user == null || user.getUserName() == null || user.getUserName().isEmpty()) {
            return "用户名不能为空";
        }
        user.setUserName(user.getUserName().trim());
        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            return "密码不能为空";
        }

        // 查找用户是否存在
        User existingUser = getUserByUserName(user.getUserName());
        if (existingUser != null) {
            return "用户已存在";
        }
        existingUser = new User();
        existingUser.setUserName(user.getUserName());
        // 对密码进行 MD5 加密
        String salt = MD5Util.generateSalt(); // 生成盐值
        String encryptedPassword = MD5Util.encrypt(user.getPassword() + salt);
        existingUser.setPassword(encryptedPassword);
        existingUser.setSalt(salt);
        //添加用户时关联角色信息
        existingUser.setRoles(user.getRoles());
        // 保存用户到数据库
        userRepository.save(existingUser);
        return "注册成功"+existingUser.getUserName();
    }

    @Override
    public String updateUser(User user) {
        //修改密码
        if (user == null || user.getUserName() == null || user.getUserName().isEmpty()) {
            return "用户名不能为空";
        }
        user.setUserName(user.getUserName().trim());
        if(user.getPassword() == null || user.getPassword().isEmpty()) {
            return "密码不能为空";
        }
        // 查找用户是否存在
        User existingUser = getUserByUserName(user.getUserName());
        if (existingUser == null) {
            return "用户不存在";
        }

        // 对密码进行 MD5 加密
        String salt = MD5Util.generateSalt(); // 生成盐值
        String encryptedPassword = MD5Util.encrypt(user.getPassword() + salt);
        existingUser.setPassword(encryptedPassword);
        existingUser.setSalt(salt);
        existingUser.setRoles(user.getRoles());// 保持原有角色不变


        userRepository.save(existingUser);
        return "用户信息更改成功";
    }

    @Override
    @Transactional
    public String deleteUser(User user) {
        if (user == null) {
            return "用户信息不能为空";
        }
        if (user.getUserId() == -1L) {
            return "用户ID不能为空";
        }
        User existingUser = userRepository.findById(user.getUserId()).orElse(null);
        if (existingUser == null) return "用户不存在";
        // 复制一份避免并发修改异常
        Set<Role> rolesCopy = new HashSet<>(existingUser.getRoles());
        for (Role r : rolesCopy) {
            r.getUsers().remove(existingUser); // 确保在拥有方移除
        }
        existingUser.getRoles().clear(); // 清除当前用户的所有角色关联
        userRepository.flush(); // 强制同步到数据库，确保关系被移除
        userRepository.delete(existingUser);

        return "用户删除成功";
    }


}
