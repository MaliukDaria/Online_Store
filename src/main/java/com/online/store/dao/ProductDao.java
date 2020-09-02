package com.online.store.dao;

import com.online.store.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Product create(Product product);

    Optional<Product> get(Long productId);

    Product update(Product product);

    boolean delete(Long productId);

    List<Product> getAll();
}
