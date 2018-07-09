package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.RBACPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<RBACPermission, Integer> {

}
