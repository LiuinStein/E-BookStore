package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
