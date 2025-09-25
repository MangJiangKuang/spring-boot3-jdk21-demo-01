# Shiro权限框架设计示例

## 设计思路
- 用户（User）拥有角色（Role），角色拥有权限（Permission）。
- 使用Shiro自定义Realm实现认证和授权。
- Controller通过Shiro注解进行权限控制。

## 运行方式
1. 启动Spring Boot项目。
2. 访问`/admin`和`/user`接口，需登录并拥有相应权限。

