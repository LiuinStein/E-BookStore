package com.shaoqunliu.demo.estore.service.impl;

import com.alibaba.fastjson.JSON;
import com.shaoqunliu.demo.estore.po.Order;
import com.shaoqunliu.demo.estore.po.OrderItem;
import com.shaoqunliu.demo.estore.repository.OrderItemRepository;
import com.shaoqunliu.demo.estore.repository.OrderRepository;
import com.shaoqunliu.demo.estore.service.OrderService;
import com.shaoqunliu.demo.estore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("myOrderService")
public class MyOrderService implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final ShoppingCartService cartService;

    @Autowired
    public MyOrderService(OrderRepository orderRepository, OrderItemRepository itemRepository, ShoppingCartService cartService) {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.cartService = cartService;
    }

    private String getCurrentUserId() {
        String auth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return JSON.parseObject(auth).getString("id");
    }

    @Override
    @Transactional
    public void addOrder(Order order) {
        List<OrderItem> items = order.getItems();
        order.setItems(null);
        long total = 0L;
        for (OrderItem item: items) {
            total += item.getPrice() * item.getQuantity();
        }
        order.setTotal(total);
        order.setTime(new Date());
        order.setPayer(Long.parseLong(getCurrentUserId()));
        order.setState((byte) 1);
        Long orderId = orderRepository.save(order).getId();
        items.forEach(x -> x.setOrderId(orderId));
        itemRepository.saveAll(items);
        cartService.clearShoppingCartItems(items);
    }

    @Override
    public List<Order> findOrderByPayerId(Long payer) {
        return orderRepository.findByPayer(payer);
    }

    @Override
    public List<Order> findOrderById(Long id) {
        Optional<Order> order = orderRepository.findById(id);
        List<Order> result = new ArrayList<>();
        order.ifPresent(result::add);
        return result;
    }
}
