package com.online.store.service;

import com.online.store.model.Product;

public interface ProductService extends GenericService<Product, Long> {
    Product update(Product product);
}
