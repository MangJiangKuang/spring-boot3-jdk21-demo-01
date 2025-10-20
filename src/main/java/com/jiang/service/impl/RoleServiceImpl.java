package com.jiang.service.impl;

import com.jiang.entity.Role;
import com.jiang.repository.RoleRepository;
import com.jiang.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
@Service
public class RoleServiceImpl  implements RoleService {
    @Resource
    RoleRepository roleRepository;
    @Override
    public String getRoleNameById(Long roleId) {
        return "";
    }

    @Override
    public String getRolePermissionsById(Long roleId) {
        return "";
    }

    @Override
    public String addRole(Role role) {
        //添加角色前需要先判断角色是否存在
        if(roleRepository.findByRoleName(role.getRoleName()).isPresent()){
            return "角色已存在";
        }
        role.clearMenus();
        Role save = roleRepository.save(role);
        return "添加角色成功";
    }

    @Override
    public String deleteRole(Role role) {
        //删除角色前需要先删除角色对应的用户角色关联
        roleRepository.delete(role);
        return "删除角色成功";
    }
}
