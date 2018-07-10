package com.shaoqunliu.demo.estore.po;

import com.shaoqunliu.demo.estore.validation.groups.cart.AddShoppingCart;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name = "t_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @NotNull(groups = {
            AddShoppingCart.class
    })
    private Long itemId;

    @PositiveOrZero
    @NotNull(groups = {
            AddShoppingCart.class
    })
    private Integer quantity;

    @PositiveOrZero
    @NotNull(groups = {
            AddShoppingCart.class
    })
    private Long price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}