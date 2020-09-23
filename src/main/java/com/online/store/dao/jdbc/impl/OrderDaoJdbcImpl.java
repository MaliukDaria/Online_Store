package com.online.store.dao.jdbc.impl;

import com.online.store.dao.OrderDao;
import com.online.store.model.Order;

import java.util.List;
import java.util.Optional;

public class OrderDaoJdbcImpl implements OrderDao {
    @Override
    public List<Order> getUserOrders(Long userId) {
        return null;
    }

    @Override
    public Order create(Order item) {
        return null;
    }

    @Override
    public Order update(Order item) {
        return null;
    }

    @Override
    public Optional<Order> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }
}
