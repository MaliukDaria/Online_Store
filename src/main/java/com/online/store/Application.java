package com.online.store;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.service.ProductService;

public class Application {
    private static Injector injector = Injector.getInstance("com.online.store");

    public static void main(String[] args) {
        ProductService productService = (ProductService) injector.getInstance(ProductService.class);
        Product phone = new Product("Samsung", 500);
        Product car = new Product("Audi", 100500);
        Product notebook = new Product("Asus", 1500);
        productService.create(phone);
        productService.create(car);
        productService.create(notebook);
        System.out.println(productService.getAll());
        productService.delete(2L);
        System.out.println(productService.getAll());
        Product anotherNotebook = new Product("Dell",300);
        anotherNotebook.setId(3L);
        productService.update(anotherNotebook);
        System.out.println(productService.getAll());
        System.out.println(productService.get(1L));
    }
}
