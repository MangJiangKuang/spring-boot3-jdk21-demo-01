package com.jiang.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor // Lombok注解，自动生成getter、setter、toString等方法
// 继承自父类的equals和hashCode方法
@Accessors(chain = true) // 支持链式调用
@Entity(name = "user")  // 指定数据库表名为users
public class User {

    @Id
    @GeneratedValue(generator = "snowflakeId") // 指定主键生成策略为自定义的雪花算法
    @GenericGenerator(name = "snowflakeId",strategy = "com.jiang.utils.SnowflakeIdGenerator")// 使用自定义的雪花算法生成器
    private long userId;
    private String userName;
    private String password;
    //配置用户的角色信息

    //不需要提交
    private String salt; // 用于存储加密盐值
    //用户和角色多对多关系
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId")
    )
    @ToString.Exclude // 避免递归调用
    private Set<Role> roles = new HashSet<>();
    //添加多个角色的方法 保证原角色不变的情况下做一个去重操作
    public void setRoles(Set<Role> newRoles) {
        // 移除当前用户的所有角色关联
        for (Role role : new HashSet<>(roles)) {
            removeRole(role);
        }
        // 添加新的角色关联
        if (newRoles != null) {
            for (Role role : newRoles) {
                addRole(role);
            }
        }
    }
    // 移除当前用户的所有角色关联方法
    public void clearRoles() {
        for (Role role : new HashSet<>(roles)) {
            removeRole(role);
        }
    }
    public void addRole(Role m) {
        if (m == null) return;
         roles.add(m);
        m.getUsers().add(this);
    }

    public void removeRole(Role m) {
        if (m == null) return;
        roles.remove(m);
        m.getUsers().remove(this);
    }

    public void clearRole() {
        for (Role m : new HashSet<>(roles)) {
            removeRole(m);
        }
    }
    //获取用户的角色信息

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        User user = (User) o;
        return getUserId() != -1 && Objects.equals(getUserId(), user.getUserId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
