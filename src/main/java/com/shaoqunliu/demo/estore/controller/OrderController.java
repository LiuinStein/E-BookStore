package com.shaoqunliu.demo.estore.controller;

import com.shaoqunliu.demo.estore.po.Order;
import com.shaoqunliu.demo.estore.service.OrderService;
import com.shaoqunliu.demo.estore.validation.groups.cart.AddShoppingCart;
import com.shaoqunliu.demo.estore.validation.groups.order.SubmitOrder;
import com.shaoqunliu.demo.estore.vo.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public RestfulResult submitOrder(@RequestBody @Validated({SubmitOrder.class, AddShoppingCart.class}) Order order) {
        orderService.addOrder(order);
        return new RestfulResult(0, "Order has been submitted!", new HashMap<>());
    }
}
