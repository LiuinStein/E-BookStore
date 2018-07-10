package com.shaoqunliu.demo.estore.controller;

import com.alibaba.fastjson.JSON;
import com.shaoqunliu.demo.estore.po.OrderItem;
import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.po.RedisShoppingCart;
import com.shaoqunliu.demo.estore.validation.groups.cart.AddShoppingCart;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/cart")
public class ShoppingCartController {

    private final ValueOperations<String, RedisShoppingCart> shoppingCartValueOperations;

    @Autowired
    public ShoppingCartController(ValueOperations<String, RedisShoppingCart> shoppingCartValueOperations) {
        this.shoppingCartValueOperations = shoppingCartValueOperations;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addShoppingCart(@RequestBody @Validated({AddShoppingCart.class}) OrderItem item) {
        String auth = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uid = JSON.parseObject(auth).getString("id");
        RedisShoppingCart cart = shoppingCartValueOperations.get(uid);
        if (cart == null) {
            cart = new RedisShoppingCart();
            cart.setId(Long.parseLong(uid));
            List<OrderItem> items = new ArrayList<>();
            items.add(item);
            cart.setOrderItems(items);
        } else {
            cart.getOrderItems().add(item);
        }
        shoppingCartValueOperations.set(uid, cart);
        return new RestfulResult(0, "Shopping cart add successfully", new HashMap<>());
    }
}
