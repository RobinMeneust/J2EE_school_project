package j2ee_project.controller.user.customer;

import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/add-customer")
public class AddCustomerController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/dashboard/add/addCustomer.jsp");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Customer customer = new Customer();

        customer.setLastName(request.getParameter("last-name"));
        customer.setFirstName(request.getParameter("first-name"));
        customer.setPassword(request.getParameter("password"));

        Address address = new Address();
        address.setStreetAddress(request.getParameter("street"));
        address.setPostalCode(request.getParameter("postal-code"));
        address.setCity(request.getParameter("city"));
        address.setCountry(request.getParameter("country"));
        customer.setAddress(address);

        customer.setEmail(request.getParameter("email"));
        customer.setPhoneNumber((request.getParameter("phone-number").isEmpty()) ? null : request.getParameter("phone-number"));

        CustomerDAO.addCustomer(customer);

        try {
            response.sendRedirect("dashboard?tab=customers");
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}