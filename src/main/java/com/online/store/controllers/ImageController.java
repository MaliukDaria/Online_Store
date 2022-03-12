package com.online.store.controllers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageController extends HttpServlet {
    private static final String IMAGE_FOLDER = "images/";
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String imageName = request.getParameter("imageName");
        response.setContentType("image/png");
        BufferedImage bi = ImageIO.read(Objects.requireNonNull(
                        getClass().getClassLoader().getResourceAsStream(IMAGE_FOLDER + imageName)));
        OutputStream out = response.getOutputStream();
        ImageIO.write(bi, "png", out);
        out.close();
    }
}
