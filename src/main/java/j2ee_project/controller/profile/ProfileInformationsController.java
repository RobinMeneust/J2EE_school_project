package j2ee_project.controller.profile;

import j2ee_project.dao.profile.CustomerDAO;
import j2ee_project.dao.profile.UserDAO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/profile-informations")
public class ProfileInformationsController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("customerId");
        int customerId;
        customerId = 2;

        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        try {
            Customer customer = CustomerDAO.getCustomer(customerId);
            request.setAttribute("customer", customer);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=1");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdStr = request.getParameter("id");
        String userAddressIdStr = request.getParameter("addressId");
        int userId=0;
        int userAdressId=0;

        if(userIdStr != null && !userIdStr.trim().isEmpty()) {
            try {
                userId = Integer.parseInt(userIdStr);
            } catch(Exception ignore) {}
        }
        if(userAddressIdStr != null && !userAddressIdStr.trim().isEmpty()) {
            try {
                userAdressId = Integer.parseInt(userAddressIdStr);
            } catch(Exception ignore) {}
        }

        Customer customer = new Customer();

        customer.setId(userId);
        customer.setFirstName(request.getParameter("userFirstName"));
        customer.setLastName(request.getParameter("userLastName"));
        customer.setEmail(request.getParameter("userEmail"));
        customer.setPassword(request.getParameter("userPassword"));
        customer.setPhoneNumber(request.getParameter("userPhoneNumber"));

        Address address = new Address();
        address.setStreetAddress(request.getParameter("userAddress"));
        address.setPostalCode(request.getParameter("userPostalCode"));
        address.setCity(request.getParameter("userCity"));
        address.setCountry(request.getParameter("userCountry"));
        address.setId(userAdressId);

        customer.setAddress(address);
        UserDAO.modifyCustomer(customer);

        try {
            Customer customert = CustomerDAO.getCustomer(userId);
            request.setAttribute("customer", customert);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=1");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
