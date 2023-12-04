package j2ee_project.controller.order;

import j2ee_project.dao.MailDAO;
import j2ee_project.dao.discount.DiscountDAO;
import j2ee_project.dao.loyalty.LoyaltyAccountDAO;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.dao.order.OrdersDAO;
import j2ee_project.model.Discount;
import j2ee_project.model.Mail;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.order.*;
import j2ee_project.model.user.Customer;
import j2ee_project.service.AuthService;
import j2ee_project.service.MailManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;

/**
 * This class is a servlet used to get the receipt page. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/receipt")
public class GetReceiptPageController extends HttpServlet
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

        if(order.getOrderStatus() == OrderStatus.WAITING_PAYMENT) {
            Cart cart = CartDAO.getCartFromCustomerId(customer.getId());
            Discount discount = cart.getDiscount();
            if(discount != null && customer.getLoyaltyAccount() != null && customer.getLoyaltyAccount().getAvailableDiscounts() != null && customer.getLoyaltyAccount().getAvailableDiscounts().contains(discount)) {
                LoyaltyAccountDAO.removeDiscount(customer.getLoyaltyAccount(), discount);
            }

            OrdersDAO.setStatus(order, OrderStatus.PREPARING);
            CartDAO.removeCart(customer.getCart());
            sendReceiptMail(customer, order);

            // Refresh the user's cart
            customer.setCart(null);
            session.setAttribute("user", customer);
        }

        request.setAttribute("order",order);


        try {
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/views/receipt.jsp");
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void sendReceiptMail(Customer customer, Orders order) {
        Mail mail = new Mail();
        MailManager mailManager = MailManager.getInstance();

        try
        {
            mail.setFromAddress("jeewebproject@gmail.com");
            mail.setToAddress(customer.getEmail());
            mail.setSubject("Receipt of the order n°"+order.getId());
            mail.setBody("Hello,\nYou will find your receipt below:\n\n"+order);
            mail.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
            MailDAO.addMail(mail);
            mailManager.send(mail);
        }
        catch(Exception ignore) {}
    }
}
