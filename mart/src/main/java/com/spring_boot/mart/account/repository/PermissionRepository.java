package com.spring_boot.mart.account.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.spring_boot.mart.account.entity.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByRole(String role);
    List<Permission> findByRoleAndIsActive(String role, Integer isActive);
}
