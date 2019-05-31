package com.wzy.springboot.security;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wzy
 */
public interface SysUserRepository extends JpaRepository<SysUser, Long> {

    SysUser findByUsername(String username);
}
