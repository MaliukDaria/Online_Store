package com.online.store.db;

import com.online.store.model.Order;
import com.online.store.model.Product;
import com.online.store.model.User;
import com.online.store.model.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static Long productId = 0l;
    private static Long userId = 0l;
    private static Long shoppingCartId = 0l;
    private static Long orderId = 0l;
    public static List<User> users = new ArrayList<>();
    public static List<Product> products = new ArrayList<>();
    public static List<ShoppingCart> shoppingCarts = new ArrayList<>();
    public static List<Order> orders = new ArrayList<>();

    public static void addProduct(Product product) {
        product.setId(++productId);
        products.add(product);
    }

    public static void addUser(User user) {
        user.setId(++userId);
        users.add(user);
    }

    public static void addShoppingCart(ShoppingCart shoppingCart) {
        shoppingCart.setId(++shoppingCartId);
        shoppingCarts.add(shoppingCart);
    }

    public static void addOrder(Order order) {
        order.setId(++orderId);
        orders.add(order);
    }
}
