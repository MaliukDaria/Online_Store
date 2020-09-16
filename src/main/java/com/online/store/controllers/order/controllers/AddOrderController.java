package com.online.store.controllers.order.controllers;

import com.online.store.lib.Injector;
import com.online.store.service.OrderService;
import com.online.store.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddOrderController extends HttpServlet {
    private static final String USER_ID = "userId";
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/orders/user.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        orderService.completeOrder(shoppingCartService.getByUserId(
                (Long) req.getSession().getAttribute(USER_ID)));
        resp.sendRedirect(req.getContextPath() + "/orders/user");
    }
}

