package j2ee_project.controller.discount;

import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.model.Discount;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/add-discount")
public class AddDiscountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/addDiscount.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Discount discount = new Discount();

        discount.setName(request.getParameter("name"));

        Date startDate = Date.valueOf(request.getParameter("start-date"));
        discount.setStartDate(startDate);
        Date endDate = Date.valueOf(request.getParameter("end-date"));
        discount.setEndDate(endDate);

        discount.setDiscountPercentage(Integer.parseInt(request.getParameter("discount-percentage")));
        DiscountDAO.addDiscount(discount);

        try {
            response.sendRedirect("dashboard");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}