package com.online.store.service;

import com.online.store.model.Order;
import com.online.store.model.ShoppingCart;
import java.util.List;

public interface OrderService extends GenericService<Order, Long> {
    Order completeOrder(ShoppingCart shoppingCart);

    List<Order> getUserOrders(Long userId);
}
