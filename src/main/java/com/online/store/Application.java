package com.online.store;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.ShoppingCart;
import com.online.store.model.User;
import com.online.store.service.OrderService;
import com.online.store.service.ProductService;
import com.online.store.service.ShoppingCartService;
import com.online.store.service.UserService;

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
        User dasha = new User("Dasha", "login", "password");
        User alex = new User("Alex", "hello", "12345");
        User bob = new User("Bob", "bobs login", "qwerty");
        UserService userService = (UserService) injector.getInstance(UserService.class);
        userService.create(dasha);
        userService.create(alex);
        userService.create(bob);
        ShoppingCart dashaShoppingCart = new ShoppingCart(dasha.getId());
        System.out.println(userService.getAll());
        dasha.setLogin("MyLogin");
        dasha.setPassword("password123");
        userService.update(dasha);
        System.out.println(userService.get(dasha.getId()));
        userService.delete(dasha.getId());
        System.out.println(userService.getAll());
        ShoppingCartService shoppingCartService =
                (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
        ShoppingCart alexShoppingCart = new ShoppingCart(alex.getId());
        ShoppingCart bobShoppingCart = new ShoppingCart(bob.getId());
        shoppingCartService.create(alexShoppingCart);
        shoppingCartService.create(bobShoppingCart);
        shoppingCartService.addProduct(alexShoppingCart, car);
        shoppingCartService.addProduct(alexShoppingCart, phone);
        shoppingCartService.addProduct(alexShoppingCart, notebook);
        shoppingCartService.addProduct(bobShoppingCart, phone);
        shoppingCartService.addProduct(bobShoppingCart, notebook);
        shoppingCartService.addProduct(bobShoppingCart, car);
        System.out.println(shoppingCartService.getByUserId(bob.getId()));
        shoppingCartService.deleteProduct(bobShoppingCart, phone);
        System.out.println(shoppingCartService.getByUserId(bob.getId()));
        shoppingCartService.clear(alexShoppingCart);
        System.out.println(shoppingCartService.getByUserId(alex.getId()));
        shoppingCartService.delete(alexShoppingCart);
        OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        System.out.println(shoppingCartService.getByUserId(bob.getId()));
        orderService.completeOrder(bobShoppingCart);
        System.out.println(shoppingCartService.getByUserId(bob.getId()));
        System.out.println(orderService.getUserOrders(bob.getId()));
        Product apple = new Product("Apple", 500);
        Product banana = new Product("Banana", 100500);
        Product candy = new Product("Candy", 1500);
        productService.create(apple);
        productService.create(banana);
        productService.create(candy);
        shoppingCartService.addProduct(bobShoppingCart, apple);
        shoppingCartService.addProduct(bobShoppingCart, banana);
        shoppingCartService.addProduct(bobShoppingCart, candy);
        System.out.println(shoppingCartService.getByUserId(bob.getId()));
        orderService.completeOrder(bobShoppingCart);
        System.out.println(orderService.getUserOrders(bob.getId()));
        System.out.println(orderService.get(2L));
        orderService.delete(1L);
        System.out.println(orderService.getAll());
    }
}
