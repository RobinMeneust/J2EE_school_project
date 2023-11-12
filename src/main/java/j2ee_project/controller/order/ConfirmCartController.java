package j2ee_project.controller.order;

import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.service.CartManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import j2ee_project.model.user.Customer;

import java.io.IOException;
import java.util.Set;

/**
 * This class is a servlet used to confirm the user's cart and redirect him to a payment page. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/confirm-cart")
public class ConfirmCartController extends HttpServlet {
    /**
     * Confirm the current cart
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        HttpSession session = request.getSession();
        Customer customer = null; // TODO: check if the user is connected and if he is, set this var
        Cart cart;

        cart = CartManager.getCart(session, customer);

        Set<CartItem> cartItems = cart.getCartItems();


        if(customer == null) {
            //TODO: Redirect to login page
        }

        if(cartItems == null) {
            //TODO: Return error
        }

        for(CartItem item : cartItems) {
            if(item.getQuantity() > item.getProduct().getStockQuantity()) {
                //TODO: Return error
            }
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
