package com.jiang.service;

import com.jiang.entity.Role;

public interface RoleService {
    //通过角色id获取角色名称
    String getRoleNameById(Long roleId);
    //通过角色id获取角色权限
    String getRolePermissionsById(Long roleId);
    //添加角色
    String addRole(Role role);

    String deleteRole(Role role);
}
