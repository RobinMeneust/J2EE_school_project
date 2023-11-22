package j2ee_project.controller.profile;

import j2ee_project.dao.profile.CustomerDAO;
import j2ee_project.model.user.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/order-history")
public class OrdersHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerIdStr = request.getParameter("id");
        int customerId = 0;

        if(customerIdStr != null && !customerIdStr.trim().isEmpty()) {
            try {
                customerId = Integer.parseInt(customerIdStr);
            } catch(Exception ignore) {}
        }

        try{
            Customer customer = CustomerDAO.getCustomer(customerId);
            request.setAttribute("customer", customer);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=3&has-loyalty-account=1");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
