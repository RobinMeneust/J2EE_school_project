package j2ee_project.controller.profile;

import j2ee_project.dao.profile.CustomerDAO;
import j2ee_project.dao.profile.LoyaltyDAO;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import j2ee_project.model.user.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hibernate.Session;

import java.io.IOException;
import java.util.List;

@WebServlet("/loyalty-redeem")
public class LoyaltyRedeemController extends HttpServlet {

    /**
     * get the loyalty account of the customer as well as loyalty levels
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("customerId");
        String loyaltyAccountIdStr = request.getParameter("loyaltyAccountId");
        System.out.println(customerIdStr);
        int customerId = 1;
        int loyaltyAccountId = 1;

        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        if(loyaltyAccountIdStr != null && !loyaltyAccountIdStr.trim().isEmpty()) {
            try {
                loyaltyAccountId = Integer.parseInt(loyaltyAccountIdStr);
            } catch(Exception ignore) {}
        }

        try {
            List<LoyaltyLevel> loyaltyLevels = LoyaltyDAO.getLoyaltyLevels();
            LoyaltyAccount loyaltyAccount = LoyaltyDAO.getLoyaltyAccount(loyaltyAccountId);
            Customer customer = CustomerDAO.getCustomer(customerId);

            request.setAttribute("customer", customer);
            request.setAttribute("loyaltyAccount", loyaltyAccount);
            request.setAttribute("loyaltyLevels", loyaltyLevels);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=2");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    /**
     *
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the POST could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String j;
    }
}