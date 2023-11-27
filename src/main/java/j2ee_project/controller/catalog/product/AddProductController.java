package j2ee_project.controller.catalog.product;

import j2ee_project.dao.catalog.category.CategoryDAO;
import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.model.catalog.Category;
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
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addProduct.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();

        product.setName(request.getParameter("name"));
        product.setStockQuantity(Integer.parseInt(request.getParameter("stock-quantity")));
        product.setUnitPrice(Integer.parseInt(request.getParameter("unit-price")));
        product.setDescription(request.getParameter("description"));
        String weight = request.getParameter("weight");
        if (weight != null){
            product.setWeight(Float.valueOf(weight));
        }

        int categoryId = Integer.parseInt(request.getParameter("category"));
        Category category = CategoryDAO.getCategory(categoryId);
        product.setCategory(category);

        ProductDAO.addProduct(product);

        try {
            response.sendRedirect("dashboard?tab=products");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}