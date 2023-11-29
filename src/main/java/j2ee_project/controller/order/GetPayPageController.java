package j2ee_project.controller.order;

import j2ee_project.dao.order.OrdersDAO;
import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import j2ee_project.model.user.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * This class is a servlet used to get the payment page . It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/pay")
public class GetPayPageController extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object obj = session.getAttribute("user");
        if(!(obj instanceof Customer)) {
            //TODO: Redirect to login page with an error
            response.sendRedirect("login");
        }

        //TODO: Check if the given order id (order-id) is in the state WAITING_PAYMENT
        String orderId = request.getParameter("order-id");
        Orders order = OrdersDAO.getOrder(orderId);

        if(order == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No order is associated to this ID");
            return;
        }

        if(order.getOrderStatus() != OrderStatus.WAITING_PAYMENT) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You have already paid for this order");
            return;
        }

        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/pay.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

}
