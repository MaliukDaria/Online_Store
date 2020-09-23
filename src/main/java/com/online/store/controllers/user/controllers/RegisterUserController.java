package com.online.store.controllers.user.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.Role;
import com.online.store.model.ShoppingCart;
import com.online.store.model.User;
import com.online.store.service.ShoppingCartService;
import com.online.store.service.UserService;
import java.io.IOException;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterUserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final UserService userService = (UserService) injector.getInstance(UserService.class);
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/users/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        req.setAttribute("login", login);
        if (userService.getByLogin(login).isPresent()) {
            req.setAttribute("errorLoginMessage", "User with this login is already registered");
            req.getRequestDispatcher("/WEB-INF/views/users/registration.jsp").forward(req, resp);
        }
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");
        if (password.equals(repeatPassword)) {
            User user = new User(login, password);
            user.setRoles(Set.of(Role.of("USER")));
            User createdUser = userService.create(user);
            ShoppingCart usersShoppingCart = new ShoppingCart(createdUser.getId());
            shoppingCartService.create(usersShoppingCart);
            resp.sendRedirect(req.getContextPath() + "/users/logout");
        } else {
            req.setAttribute("errorMessage", "The passwords does not match");
            req.getRequestDispatcher("/WEB-INF/views/users/registration.jsp").forward(req, resp);
        }
    }
}
