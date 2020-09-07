package com.online.store.dao;

import com.online.store.model.Order;
import java.util.List;
import java.util.Optional;

public interface OrderDao {
    Order create(Order order);

    Order update(Order order);

    Optional<Order> getById(Long id);

    List<Order> getUserOrders(Long userId);

    boolean delete(Long id);

    List<Order> getAll();
}
