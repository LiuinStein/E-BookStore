package com.shaoqunliu.demo.estore.service.impl;

import com.shaoqunliu.demo.estore.po.Book;
import com.shaoqunliu.demo.estore.po.Order;
import com.shaoqunliu.demo.estore.po.OrderItem;
import com.shaoqunliu.demo.estore.po.RBACUser;
import com.shaoqunliu.demo.estore.repository.BookRepository;
import com.shaoqunliu.demo.estore.repository.OrderItemRepository;
import com.shaoqunliu.demo.estore.repository.OrderRepository;
import com.shaoqunliu.demo.estore.service.OrderService;
import com.shaoqunliu.demo.estore.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service("myOrderService")
public class MyOrderService implements OrderService {

    private final BookRepository bookRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository itemRepository;
    private final ShoppingCartService cartService;

    @Autowired
    public MyOrderService(BookRepository bookRepository, OrderRepository orderRepository, OrderItemRepository itemRepository, ShoppingCartService cartService) {
        this.bookRepository = bookRepository;
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.cartService = cartService;
    }

    private String getCurrentUserId() {
        return ((RBACUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().toString();
    }

    @Override
    @Transactional(rollbackOn = {
            Exception.class
    })
    public void addOrder(Order order) throws ConstraintViolationException {
        List<OrderItem> items = order.getItems();
        order.setItems(null);
        long total = 0L;
        for (OrderItem item : items) {
            total += item.getPrice() * item.getQuantity();
            Optional<Book> info = bookRepository.findById(item.getItemId());
            if (!info.isPresent() || info.get().getRemain() < item.getQuantity()) {
                throw new ConstraintViolationException("The book's remain quantity less than ordered or the ordered items doesn't exist.", null);
            }
            info.get().setRemain(info.get().getRemain() - item.getQuantity());
            bookRepository.save(info.get());
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
    @Transactional(rollbackOn = {
            Exception.class
    })
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
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
