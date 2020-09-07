package com.online.store.dao;

import com.online.store.model.ShoppingCart;
import java.util.List;
import java.util.Optional;

public interface ShoppingCartDao {
    ShoppingCart create(ShoppingCart shoppingCart);

    ShoppingCart update(ShoppingCart shoppingCart);

    Optional<ShoppingCart> getByUserId(Long userId);

    Optional<ShoppingCart> getById(Long id);

    boolean delete(Long id);

    List<ShoppingCart> getAll();
}
