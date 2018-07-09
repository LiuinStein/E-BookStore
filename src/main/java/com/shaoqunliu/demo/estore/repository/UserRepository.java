package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.RBACUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RBACUser, Long> {

}
