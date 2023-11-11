package j2ee_project.controller.profile;

import j2ee_project.dao.profile.CustomerDAO;
import j2ee_project.dao.profile.UserDAO;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
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
        int customerId; //= Integer.parseInt(customerIdStr);
        customerId = 2;

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
        // informations taken from form
        String userName = request.getParameter("userName");
        String userSurname = request.getParameter("userSurname");
        String userEmail = request.getParameter("userEmail");
        String userPassword = request.getParameter("userPassword");
        String userPhoneNumber = request.getParameter("userPhoneNumber");
        String userAddress = request.getParameter("userAddress");
        String userPostalCode = request.getParameter("userPostalCode");
        String userCity = request.getParameter("userCity");
        String userCountry = request.getParameter("userCountry");
    }
}
