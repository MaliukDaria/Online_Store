package com.online.store.controllers.user.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.ShoppingCart;
import com.online.store.model.User;
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User dasha = new User("dasha", "12345");
        User ruslan = new User("ruslan", "54321");
        userService.create(dasha);
        userService.create(ruslan);
        ShoppingCart dashaShoppingCart = new ShoppingCart(dasha.getId());
        ShoppingCart ruslanShoppingCart = new ShoppingCart(ruslan.getId());
        shoppingCartService.create(dashaShoppingCart);
        shoppingCartService.create(ruslanShoppingCart);
        req.getRequestDispatcher("/WEB-INF/views/users/injectData.jsp").forward(req, resp);
    }
}
