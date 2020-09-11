package com.online.store.controllers.order.controllers;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.service.OrderService;
import com.online.store.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetOrderDetailsController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final OrderService orderService =
            (OrderService) injector.getInstance(OrderService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Long orderId = Long.valueOf(req.getParameter("id"));
        List<Product> products = orderService.getById(orderId).getProducts();
        req.setAttribute("products", products);
        req.setAttribute("orderId", orderId);
        req.getRequestDispatcher("/WEB-INF/views/orders/orderDetails.jsp").forward(req, resp);
    }
}
