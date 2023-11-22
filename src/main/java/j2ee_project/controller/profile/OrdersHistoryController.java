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
        System.out.println("entered order controller");
        String idCustomerStr = request.getParameter("id");
        System.out.println(idCustomerStr);
        int idCustomer = 0;

        if(idCustomerStr != null && !idCustomerStr.trim().isEmpty()) {
            try {
                idCustomer = Integer.parseInt(idCustomerStr);
            } catch(Exception ignore) {}
        }
        System.out.println(idCustomer);
        try{
            Customer customer = CustomerDAO.getCustomer(idCustomer);
            request.setAttribute("customer", customer);
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=3");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
