package j2ee_project.controller.user.customer.edit;

import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.dao.user.CustomerDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/edit-customer")
public class EditCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("customer", CustomerDAO.getCustomer(Integer.parseInt(request.getParameter("id"))));
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/edit/editCustomer.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}