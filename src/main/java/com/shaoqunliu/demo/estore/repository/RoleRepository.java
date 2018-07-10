package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.RBACRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RBACRole, Byte> {

}
