package com.online.store.controllers.user.controllers;

import com.online.store.lib.Injector;
import com.online.store.service.ShoppingCartService;
import com.online.store.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long userId = Long.valueOf(req.getParameter("id"));
        userService.delete(userId);
        shoppingCartService.delete(shoppingCartService.getByUserId(userId).getId());
        resp.sendRedirect(req.getContextPath() + "/users/all");
    }
}
