package j2ee_project.controller.user.customer;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.user.Customer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/delete-customer")
public class DeleteCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("id");
        int customerId = -1;

        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        if(customerId<=0) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer ID must be positive");
        }
        CustomerDAO.deleteCustomer(customerId);
        try {
            response.sendRedirect("dashboard");
        }catch (Exception err){
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}