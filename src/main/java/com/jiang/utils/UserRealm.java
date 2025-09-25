package com.jiang.utils;

import com.jiang.entity.User;
import com.jiang.repository.UserRepository;
import jakarta.annotation.Resource;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.lang.util.ByteSource;

public class UserRealm extends AuthorizingRealm {
    @Resource
    private UserRepository userRepository;

    // 授权：为已认证用户加载角色与权限
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        String username = (String) principals.getPrimaryPrincipal();
        userRepository.findByUserName(username).ifPresent(user -> {
            user.getRoles().forEach(role -> {
                info.addRole(role.getRoleName());
                role.getMenus().forEach(menu -> {
                    if (menu.getPermission() != null && !menu.getPermission().isBlank()) {
                        info.addStringPermission(menu.getPermission());
                    }
                });
            });
        });
        return info;
    }

    // 认证：用户名 + MD5(salt) 校验
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        if (username == null || username.isBlank()) {
            throw new AccountException("用户名不能为空");
        }

        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            throw new UnknownAccountException("用户不存在");
        }

        // 返回带盐的认证信息，交由 HashedCredentialsMatcher(MD5+salt) 校验
        return new SimpleAuthenticationInfo(
                user.getUserName(),
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName());
    }
}
