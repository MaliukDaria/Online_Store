package com.online.store.controllers.shoppingcart.controller;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.ShoppingCart;
import com.online.store.service.ShoppingCartService;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetProductsFromShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        List<Product> products = shoppingCart.getProducts();
        req.setAttribute("products", products);
        req.getRequestDispatcher("/WEB-INF/views/shoppingcart/products.jsp").forward(req, resp);
    }
}
