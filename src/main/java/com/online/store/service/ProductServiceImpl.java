package com.online.store.service;

import com.online.store.dao.ProductDao;
import com.online.store.lib.Inject;
import com.online.store.lib.Service;
import com.online.store.model.Product;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Inject
    ProductDao productDao;

    @Override
    public Product create(Product product) {
        productDao.create(product);
        return product;
    }

    @Override
    public Product get(Long productId) {
        return productDao.get(productId).get();
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
