package com.shaoqunliu.demo.estore.repository;

import com.shaoqunliu.demo.estore.po.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByPayer(Long payer);
}
