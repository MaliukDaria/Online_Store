package com.online.store.dao;

import com.online.store.model.Order;
import java.util.List;

public interface OrderDao extends GenericDao<Order, Long> {
    List<Order> getUserOrders(Long userId);
}
