package com.online.store.controllers.user.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.ShoppingCart;
import com.online.store.model.User;
import com.online.store.service.ProductService;
import com.online.store.service.ShoppingCartService;
import com.online.store.service.UserService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InjectTestDataController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User dasha = new User("dasha", "12345");
        User ruslan = new User("ruslan", "54321");
        User alex = new User("alex", "hello");
        userService.create(dasha);
        userService.create(ruslan);
        userService.create(alex);
        ShoppingCart dashaShoppingCart = new ShoppingCart(dasha.getId());
        ShoppingCart ruslanShoppingCart = new ShoppingCart(ruslan.getId());
        ShoppingCart alexShoppingCart = new ShoppingCart(alex.getId());
        shoppingCartService.create(dashaShoppingCart);
        shoppingCartService.create(ruslanShoppingCart);
        shoppingCartService.create(alexShoppingCart);

        Product samsung = new Product("Samsung", 200);
        Product asus = new Product("asus", 1500);
        Product iphone = new Product("iphone", 1000);
        Product audi = new Product("audi", 100500);
        Product cup = new Product("cup", 13);
        productService.create(samsung);
        productService.create(iphone);
        productService.create(asus);
        productService.create(audi);
        productService.create(cup);

        req.getRequestDispatcher("/WEB-INF/views/users/injectData.jsp").forward(req, resp);
    }
}
