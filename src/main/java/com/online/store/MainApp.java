package com.online.store;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.service.ProductService;
import java.util.Collections;

public class MainApp {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private static final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    public static void main(String[] args) {
        Product sony = new Product("Sony", 33);
        productService.create(sony);
        System.out.println(Collections.unmodifiableList(productService.getAll()));
        Product sonyFromDb = productService.getAll().stream()
                .filter(p -> p.getName().equals(sony.getName()))
                .findAny()
                .orElseThrow();
        System.out.println("expected sony with price: "
                + sony.getPrice() + "\n actual: " + productService.getById(sonyFromDb.getId()));
        sonyFromDb.setName("New Sony");
        sonyFromDb.setPrice(13);
        productService.update(sonyFromDb);
        System.out.println("expected New Sony with price: " + sonyFromDb.getPrice()
                + "\n actual: " + productService.getById(sonyFromDb.getId()));
        System.out.println("expected true\n actual: " + productService.delete(sonyFromDb.getId()));
        System.out.println(Collections.unmodifiableList(productService.getAll()));
    }
}
