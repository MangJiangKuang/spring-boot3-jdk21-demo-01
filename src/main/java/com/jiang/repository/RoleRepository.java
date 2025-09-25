package com.jiang.repository;

import com.jiang.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface RoleRepository extends JpaRepository<Role, Long> {
    // 需要实体时
    Optional<Role> findByRoleName(String roleName);
}
