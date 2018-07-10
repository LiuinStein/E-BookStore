package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.OrderItem;
import com.shaoqunliu.demo.estore.service.ShoppingCartService;
import com.shaoqunliu.demo.estore.validation.groups.cart.AddShoppingCart;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/v1/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult addShoppingCart(@RequestBody @Validated({AddShoppingCart.class}) OrderItem item) {
        shoppingCartService.addShoppingCartItem(item);
        return new RestfulResult(0, "Shopping cart add successfully", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult modifyShoppingCartItem(@RequestBody @Validated({AddShoppingCart.class}) OrderItem item) {
        shoppingCartService.modifyShoppingCartItem(item);
        return new RestfulResult(0, "Shopping cart has been successfully modified", new HashMap<>());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.OK)
    public RestfulResult findAllShoppingCartItems() {
        List<OrderItem> items = shoppingCartService.findAllShoppingCartItems();
        HashMap<String, Object> result = new HashMap<>();
        result.put("items", items);
        return new RestfulResult(0, "", result);
    }
}
