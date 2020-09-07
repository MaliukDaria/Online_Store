package com.online.store.service.impl;

import com.online.store.dao.ProductDao;
import com.online.store.lib.Inject;
import com.online.store.lib.Service;
import com.online.store.model.Product;
import com.online.store.service.ProductService;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long productId) {
        return productDao.getById(productId).get();
    }

    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public boolean delete(Long productId) {
        return productDao.delete(productId);
    }
}
