package com.online.store.dao.impl;

import com.online.store.dao.ProductDao;
import com.online.store.db.Storage;
import com.online.store.model.Product;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ProductDaoImpl implements ProductDao {
    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> getById(Long productId) {
        return Storage.products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst();
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(i -> Storage.products.get(i).getId().equals(product.getId()))
                .forEach(i -> Storage.products.set(i, product));
        return product;
    }

    @Override
    public boolean delete(Long productId) {
        return Storage.products.removeIf(p -> p.getId().equals(productId));
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }
}
