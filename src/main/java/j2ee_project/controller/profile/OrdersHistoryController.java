package j2ee_project.controller.profile;

import j2ee_project.dao.profile.OrdersDAO;
import j2ee_project.model.order.Orders;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/order-history")
public class OrdersHistoryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idCustomerStr = request.getParameter("id");
        int idCustomer = 2;

        if(idCustomerStr != null && !idCustomerStr.trim().isEmpty()) {
            try {
                idCustomer = Integer.parseInt(idCustomerStr);
            } catch(Exception ignore) {}
        }

        try{
            List<Orders> orders = OrdersDAO.getOrders(idCustomer);
            request.setAttribute("orders", orders);
            System.out.println("Size:"+OrdersDAO.getOrders(idCustomer).size());

            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/profile.jsp?active-tab=3");
            view.forward(request,response);
        }catch (Exception err){
            System.out.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
