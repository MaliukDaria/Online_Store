package com.online.store.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.Role;
import com.online.store.model.ShoppingCart;
import com.online.store.model.User;
import com.online.store.service.ProductService;
import com.online.store.service.ShoppingCartService;
import com.online.store.service.UserService;
import java.io.IOException;
import java.util.Set;
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
        User admin = new User("Admin", "admin");
        admin.setRoles(Set.of(Role.of("ADMIN")));

        User bob = new User("Bob", "1111");
        bob.setRoles(Set.of(Role.of("USER"), Role.of("ADMIN")));

        User alise = new User("Alise", "1111");
        alise.setRoles(Set.of(Role.of("USER")));
        
        admin = userService.create(admin);
        bob = userService.create(bob);
        alise = userService.create(alise);
        ShoppingCart bobShoppingCart = new ShoppingCart(bob.getId());
        ShoppingCart aliseShoppingCart = new ShoppingCart(alise.getId());
        ShoppingCart adminShoppingCart = new ShoppingCart(admin.getId());
        shoppingCartService.create(bobShoppingCart);
        shoppingCartService.create(aliseShoppingCart);
        shoppingCartService.create(adminShoppingCart);

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
