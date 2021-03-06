package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.Order;

import javax.validation.ConstraintViolationException;
import java.util.List;

public interface OrderService {

    void addOrder(Order order) throws ConstraintViolationException;

    void deleteOrder(Long id);

    List<Order> findOrderByPayerId(Long payer);

    List<Order> findOrderById(Long id);
}
