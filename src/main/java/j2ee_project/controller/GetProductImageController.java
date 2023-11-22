package j2ee_project.controller;

import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.model.catalog.Product;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class is a servlet used to get a product image from the Tomcat server (/images path). It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet(value = "/product/image")
public class GetProductImageController extends HttpServlet {
    /**
     * Get a page to send message with a contact form
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String productIdStr = request.getParameter("id");
        int productId = -1;

        if(productIdStr != null && !productIdStr.trim().isEmpty()) {
            try {
                productId = Integer.parseInt(productIdStr);
            } catch(Exception ignore) {}
        }

        if(productId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Product ID must be positive");
        }

        Product product = ProductDAO.getProduct(productId);

        File root = new File(getServletContext().getRealPath("/")).getParentFile().getParentFile();
        Path imagePath = Paths.get(root.getPath()+"/"+product.getImagePath());

        System.out.println("root: "+root.getPath());
        System.out.println("path: "+imagePath.toString());

        try (InputStream in = Files.newInputStream(imagePath)) {
            response.setContentType(getServletContext().getMimeType(imagePath.toString()));
            Files.copy(imagePath, response.getOutputStream());
        }
    }

}