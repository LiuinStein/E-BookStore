package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
