package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.OrderItem;

import java.util.List;

public interface ShoppingCartService {

    void addShoppingCartItem(OrderItem item);

    void modifyShoppingCartItem(OrderItem item);

    void clearShoppingCartItems(List<OrderItem> items);

    List<OrderItem> findAllShoppingCartItems();
}
