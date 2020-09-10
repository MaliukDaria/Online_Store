package com.online.store.controllers.shoppingcart.controller;

import com.online.store.lib.Injector;
import com.online.store.model.Product;
import com.online.store.model.ShoppingCart;
import com.online.store.service.ProductService;
import com.online.store.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddToShoppingCartController extends HttpServlet {
    private static final Long USER_ID = 1L;
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        ShoppingCart shoppingCart = shoppingCartService.getByUserId(USER_ID);
        Long productId = Long.valueOf(req.getParameter("id"));
        Product product = productService.getById(productId);
        shoppingCartService.addProduct(shoppingCart, product);
        resp.sendRedirect(req.getContextPath() + "/products/all");
    }
}
