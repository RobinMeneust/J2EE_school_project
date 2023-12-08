package j2ee_project.controller.catalog.category;

import j2ee_project.dao.catalog.category.CategoryDAO;
import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Category;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/add-category")
public class AddCategoryController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("discounts", DiscountDAO.getDiscounts());
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addCategory.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = new Category();

        category.setName(request.getParameter("name"));
        category.setDescription(request.getParameter("description"));

        String discountStr = (request.getParameter("discount").isEmpty()) ? null : request.getParameter(("discount"));
        if (discountStr != null) {
            int discountId = Integer.parseInt(discountStr);
            Discount discount = DiscountDAO.getDiscount(discountId);
            category.setDiscount(discount);
        }
        CategoryDAO.addCategory(category);

        try {
            response.sendRedirect("dashboard?tab=categories");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}