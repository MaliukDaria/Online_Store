package com.online.store.controllers.shoppingcart.controller;

import com.online.store.lib.Injector;
import com.online.store.service.ProductService;
import com.online.store.service.ShoppingCartService;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteProductFromShoppingCartController extends HttpServlet {
    private static final String USER_ID = "userId";
    private static final Injector injector = Injector.getInstance("com.online.store");
    private final ShoppingCartService shoppingCartService =
            (ShoppingCartService) injector.getInstance(ShoppingCartService.class);
    private final ProductService productService =
            (ProductService) injector.getInstance(ProductService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        Long productId = Long.valueOf(req.getParameter("id"));
        shoppingCartService.deleteProduct(
                shoppingCartService.getByUserId(
                        (Long) req.getSession().getAttribute(USER_ID)),
                productService.getById(productId));
        resp.sendRedirect(req.getContextPath() + "/shopping-cart/products");
    }
}
