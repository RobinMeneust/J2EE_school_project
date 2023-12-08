package j2ee_project.controller.order;

import j2ee_project.dao.order.OrdersDAO;
import j2ee_project.model.order.*;
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
 * This class is a servlet used to get the receipt page. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/order")
public class GetOrderPageController extends HttpServlet
{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();

        Object obj = session.getAttribute("user");
        if(!(obj instanceof Customer)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"You are not logged in");
            return;
        }

        Customer customer = (Customer) obj;

        String orderId = request.getParameter("order-id");
        Orders order = OrdersDAO.getOrder(orderId);

        if(order == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No order is associated to this ID");
            return;
        }

        request.setAttribute("order",order);

        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/order.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
