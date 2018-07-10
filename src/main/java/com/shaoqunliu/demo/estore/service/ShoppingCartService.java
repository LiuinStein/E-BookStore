package com.shaoqunliu.demo.estore.service;

import com.shaoqunliu.demo.estore.po.OrderItem;

public interface ShoppingCartService {

    void addShoppingCartItem(OrderItem item);

    void modifyShoppingCartItem(OrderItem item);
}
