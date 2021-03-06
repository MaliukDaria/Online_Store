package com.online.store.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private Long id;
    private List<Product> products;
    private Long userId;

    public ShoppingCart(Long userId) {
        this.userId = userId;
        products = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ShoppingCart{"
                + "id=" + id
                + ", userId=" + userId
                + ", products=" + toProcuctsString(products)
                + '}';
    }

    private String toProcuctsString(List<Product> products) {
        StringBuilder allProducts = new StringBuilder();
        for (Product product : products) {
            allProducts.append(product.getName()).append(" ");
        }
        return allProducts.toString();
    }
}
