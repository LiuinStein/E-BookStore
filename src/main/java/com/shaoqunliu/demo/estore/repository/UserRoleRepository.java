package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.RBACUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<RBACUserRole, Integer> {

}
