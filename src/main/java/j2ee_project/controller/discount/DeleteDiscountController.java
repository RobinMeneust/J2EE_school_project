package j2ee_project.controller.discount;

import j2ee_project.dao.discount.DiscountDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/delete-discount")
public class DeleteDiscountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String discountIdStr = request.getParameter("id");
        int discountId = -1;

        if(discountIdStr != null && !discountIdStr.trim().isEmpty()) {
            try {
                discountId = Integer.parseInt(discountIdStr);
            } catch(Exception ignore) {}
        }

        if(discountId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Discount ID must be positive");
        }
        DiscountDAO.deleteDiscount(discountId);
        try {
            response.sendRedirect("dashboard");
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}