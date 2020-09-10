package com.online.store.controllers.order.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.Order;
import com.online.store.service.OrderService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetUsersOrdersController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<Order> userOrders = orderService.getUserOrders(USER_ID);
        req.setAttribute("orders", userOrders);
        req.getRequestDispatcher("/WEB-INF/views/orders/userOrders.jsp").forward(req, resp);
    }

}
