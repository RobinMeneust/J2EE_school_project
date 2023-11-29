package j2ee_project.controller.order;

import j2ee_project.dao.catalog.product.ProductDAO;
import j2ee_project.dao.loyalty.LoyaltyLevelDAO;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.dao.user.CustomerDAO;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import j2ee_project.model.loyalty.LoyaltyProgram;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import j2ee_project.service.CartManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a servlet used to add items to the cart. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/cart/loyalty-level-discount")
public class AddLoyaltyLevelDiscountToCart extends HttpServlet {
    /**
     * Add an item to the user cart
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        BufferedReader reader = request.getReader();
        JSONTokener tokener = new JSONTokener(reader);
        JSONObject paramsObject = new JSONObject(tokener);

        int id = -1;
        try {
            id = paramsObject.getInt("id");
        } catch (Exception ignore) {}

        LoyaltyLevel loyaltyLevel = LoyaltyLevelDAO.getLoyaltyLevel(id);

        if(loyaltyLevel == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The provided loyalty level ID doesn't exist");
            return;
        }

        HttpSession session = request.getSession();

        Object obj = session.getAttribute("user");
        Customer customer = null;
        if(obj instanceof Customer) {
            customer = (Customer) obj;
        }

        if(customer == null) {
            response.sendRedirect("login");
            return;
        }

        LoyaltyAccount loyaltyAccount = customer.getLoyaltyAccount();

        if(loyaltyAccount == null || loyaltyAccount.isLevelUsed(loyaltyLevel)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The provided loyalty level has already been used or you don't have a loyalty account");
            return;
        }

        Cart cart = CartDAO.getCartFromCustomerId(customer.getId());

        if(cart == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "You don't have a cart");
            return;
        }

        CartDAO.setDiscount(cart.getId(), loyaltyLevel.getDiscount());

        // Refresh the user's cart
        customer.setCart(CartDAO.getCartFromCustomerId(customer.getId()));
        session.setAttribute("user", customer);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
