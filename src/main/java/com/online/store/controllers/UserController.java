package com.online.store.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.User;
import com.online.store.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private UserService userService = (UserService) injector.getInstance(UserService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        User dasha = new User("Dasha","dasha", "12345");
        User ruslan = new User("Ruslan","ruslan", "54321");
        userService.create(dasha);
        userService.create(ruslan);
        List<User> allUsers = userService.getAll();
        req.setAttribute("users", allUsers);
        req.getRequestDispatcher("/WEB-INF/views/users/all.jsp").forward(req, resp);
    }
}
