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
@Entity(name = "role")  // 指定数据库表名为role
public class Role {
    @Id
    @GeneratedValue(generator = "snowflakeId") // 指定主键生成策略为自定义的雪花算法
    @GenericGenerator(name = "snowflakeId",strategy = "com.jiang.utils.SnowflakeIdGenerator")// 使用自定义的雪花算法生成器
    private long roleId;
    private String roleName;
    //角色和菜单多对多关系
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
          name = "role_menu",
          joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "roleId"),
          inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "menuId")
    )
    @ToString.Exclude // 避免递归调用
    private Set<Menu> menus = new HashSet<>();
    //角色和用户多对多关系
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @ToString.Exclude // 避免递归调用
    private Set<User> users = new HashSet<>();


    public void addMenu(Menu m) {
        if (m == null) return;
        menus.add(m);
        m.getRoles().add(this);
    }

    public void removeMenu(Menu m) {
        if (m == null) return;
        menus.remove(m);
        m.getRoles().remove(this);
    }

    public void clearMenus() {
        for (Menu m : new HashSet<>(menus)) {
            removeMenu(m);
        }
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Role role = (Role) o;
        return getRoleId() != -1 && Objects.equals(getRoleId(), role.getRoleId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
