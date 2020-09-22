package com.online.store;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.Role;
import com.online.store.model.User;
import com.online.store.service.ProductService;
import com.online.store.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MainApp {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private static final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    public static final UserService userService =
            (UserService) injector.getInstance(UserService.class);

    public static void main(String[] args) {
        User dasha = new User("Dasha", "1234");
        dasha.setRoles(Set.of(Role.of("USER"), Role.of("ADMIN")));
        User createdDasha = userService.create(dasha);
        dasha.setId(createdDasha.getId());
        System.out.println("Create and get by id:\nExpected: " + dasha + "\n Actual: " + userService.getById(createdDasha.getId()));
        System.out.println("Get by Login:\nExpected: " + dasha + "\n Actual: " + userService.getByLogin(createdDasha.getLogin()));
        createdDasha.setName("Daria");
        createdDasha.setPassword("4321");
        createdDasha.setRoles(Set.of(Role.of("USER")));
        User updatedDasha = userService.update(createdDasha);
        System.out.println("Update:\nExpected: " + createdDasha + "\n Actual: " + userService.getById(updatedDasha.getId()));
        System.out.println("Delete:\nExpected true \nActual: " + userService.delete(updatedDasha.getId()));
        List<User> allUsers = userService.getAll();
        for (User user : allUsers) {
            System.out.println(user);
        }
        System.out.println();

       /* Product sony = new Product("Sony", 33);
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
        System.out.println(Collections.unmodifiableList(productService.getAll()));*/

    }
}
