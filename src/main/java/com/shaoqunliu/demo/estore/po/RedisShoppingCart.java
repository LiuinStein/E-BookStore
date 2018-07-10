package com.shaoqunliu.demo.estore.po;

import java.io.Serializable;
import java.util.List;

public class RedisShoppingCart implements Serializable {

    private Long id;
    private List<OrderItem> orderItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
}
