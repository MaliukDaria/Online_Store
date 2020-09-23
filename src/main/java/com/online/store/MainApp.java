package com.online.store;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.Role;
import com.online.store.model.ShoppingCart;
import com.online.store.model.User;
import com.online.store.service.ProductService;
import com.online.store.service.ShoppingCartService;
import com.online.store.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MainApp {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private static final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);
    public static final UserService userService =
            (UserService) injector.getInstance(UserService.class);
    public static final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    public static void main(String[] args) {
        ////////////////  Shopping Carts  //////////////////
/*        Product samsung = productService.getById(1l);
        Product iphone = productService.getById(2l);
        Product asus = productService.getById(3l);
        Product audi = productService.getById(4l);
        Product cup = productService.getById(5l);
        User alise = userService.getByLogin("Alise").get();
        ShoppingCart aliseShoppingCart = new ShoppingCart(alise.getId());
        ShoppingCart createdShoppingCart = shoppingCartService.create(aliseShoppingCart);
        shoppingCartService.addProduct(createdShoppingCart, samsung);
        shoppingCartService.addProduct(createdShoppingCart, iphone);
        shoppingCartService.addProduct(createdShoppingCart, asus);
        shoppingCartService.addProduct(createdShoppingCart, audi);
        shoppingCartService.addProduct(createdShoppingCart, cup);
        System.out.println("Get by id\nExpected samsung, iphone, asus, audi, cup\n"
                + "Actual: " + shoppingCartService.getById(createdShoppingCart.getId()));
        shoppingCartService.deleteProduct(createdShoppingCart, iphone);
        System.out.println("Get by user id\nExpected samsung, asus, audi, cup\n"
                + "Actual: " + shoppingCartService.getByUserId(alise.getId()));
        User bob = userService.getByLogin("Bob").get();
        ShoppingCart bobShoppingCart = shoppingCartService.create(new ShoppingCart(bob.getId()));
        shoppingCartService.addProduct(bobShoppingCart, samsung);
        shoppingCartService.addProduct(bobShoppingCart, audi);
        List<ShoppingCart> all = shoppingCartService.getAll();
        System.out.println("Expected 2 carts: \n(samsung, iphone, asus, audi, cup)\n(samsung, audi)\nActual: ");
        for (ShoppingCart cart:all) {
            System.out.println(cart);
        }
        System.out.println("Expected true: " + shoppingCartService.delete(createdShoppingCart.getId()));
        System.out.println("Expected 1 shopping cart: \n" + shoppingCartService.getAll());
        shoppingCartService.delete(bobShoppingCart.getId());*/
            ////////////////  Users  //////////////////
       /* User dasha = new User("Dasha", "1234");
        dasha.setRoles(Set.of(Role.of("USER"), Role.of("ADMIN")));
        User createdDasha = userService.create(dasha);
        dasha.setId(createdDasha.getId());
        System.out.println("Create and get by id:\nExpected: " + dasha
                + "\n Actual: " + userService.getById(createdDasha.getId()));
        System.out.println("Get by Login:\nExpected: " + dasha
                + "\n Actual: " + userService.getByLogin(createdDasha.getLogin()));
        createdDasha.setName("Daria");
        createdDasha.setPassword("4321");
        createdDasha.setRoles(Set.of(Role.of("USER")));
        User updatedDasha = userService.update(createdDasha);
        System.out.println("Update:\nExpected: " + createdDasha
                + "\n Actual: " + userService.getById(updatedDasha.getId()));
        System.out.println("Delete:\nExpected true \nActual: "
                + userService.delete(updatedDasha.getId()));
        List<User> allUsers = userService.getAll();
        for (User user : allUsers) {
            System.out.println(user);
        }
        System.out.println();*/
              ////////////////  Products  //////////////////
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
