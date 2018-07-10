package com.shaoqunliu.demo.estore.service.impl;

import com.alibaba.fastjson.JSON;
import com.shaoqunliu.demo.estore.po.OrderItem;
import com.shaoqunliu.demo.estore.po.RedisShoppingCart;
import com.shaoqunliu.demo.estore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service("myShoppingCartService")
public class MyShoppingCartService implements ShoppingCartService {

    private final ValueOperations<String, RedisShoppingCart> shoppingCartValueOperations;

    @Autowired
    public MyShoppingCartService(ValueOperations<String, RedisShoppingCart> shoppingCartValueOperations) {
        this.shoppingCartValueOperations = shoppingCartValueOperations;
    }

    private String getCurrentUserId() {
        String auth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return JSON.parseObject(auth).getString("id");
    }

    private RedisShoppingCart getCurrentShoppingCart() {
        String uid = getCurrentUserId();
        RedisShoppingCart cart = shoppingCartValueOperations.get(uid);
        if (cart == null) {
            cart = new RedisShoppingCart();
            cart.setId(Long.parseLong(uid));
            cart.setOrderItems(new ArrayList<>());
            shoppingCartValueOperations.set(uid, cart);
        }
        return cart;
    }

    @Override
    public void addShoppingCartItem(OrderItem item) {
        RedisShoppingCart cart = getCurrentShoppingCart();
        for (int i = 0; i < cart.getOrderItems().size(); i++) {
            if (item.getItemId().equals(cart.getOrderItems().get(i).getItemId())) {
                cart.getOrderItems().remove(i);
                break;
            }
        }
        cart.getOrderItems().add(item);
        shoppingCartValueOperations.set(getCurrentUserId(), cart, 3, TimeUnit.DAYS);
    }

    @Override
    public void modifyShoppingCartItem(OrderItem item) {
        RedisShoppingCart cart = getCurrentShoppingCart();
        for (int i = 0; i < cart.getOrderItems().size(); i++) {
            if (item.getItemId().equals(cart.getOrderItems().get(i).getItemId())) {
                cart.getOrderItems().remove(i);
                if (!item.getQuantity().equals(0)) {
                    cart.getOrderItems().add(item);
                }
                break;
            }
        }
        shoppingCartValueOperations.set(getCurrentUserId(), cart, 3, TimeUnit.DAYS);
    }
}
