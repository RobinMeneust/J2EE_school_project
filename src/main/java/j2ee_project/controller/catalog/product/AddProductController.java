package j2ee_project.controller.catalog.product;

import j2ee_project.dao.catalog.category.CategoryDAO;
import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.model.catalog.Product;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/add-product")
public class AddProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("categories", CategoryDAO.getCategories());
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/addProduct.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();



        ProductDAO.addProduct(product);

        try {
            response.sendRedirect("dashboard");
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}