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

@Accessors(chain = true) // 支持链式调用
@Entity(name = "menu")  // 指定数据库表名为menu
public class Menu {
    @Id
    @GeneratedValue(generator = "snowflakeId") // 指定主键生成策略为自定义的雪花算法
    @GenericGenerator(name = "snowflakeId",strategy = "com.jiang.utils.SnowflakeIdGenerator")// 使用自定义的雪花算法生成器
    private long menuId;
    private String menuName;
    private String menuUrl;
    private  String menuIcon;
    private int parentId;
    private int orderNum;
    private String description;
    private boolean enabled;
    private boolean isHidden;
    private String permission;
  //菜单和角色多对多关系
  @ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY)
  @ToString.Exclude
  private Set<Role> roles = new HashSet<>();

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Menu menu = (Menu) o;
        return getMenuId() != -1 && Objects.equals(getMenuId(), menu.getMenuId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
