package j2ee_project.controller.order;

import j2ee_project.dao.catalog.ProductDAO;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * This class is a servlet used to add items to the cart. It's a controller in the MVC architecture of this project.
 *
 * @author Robin MENEUST
 */
@WebServlet("/add-to-cart")
public class AddToCartController extends HttpServlet {
    private Cart getSessionCart(HttpSession session) {
        Object cartObj = session.getAttribute("cart");
        Cart cart;

        if(!(cartObj instanceof Cart)) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        } else {
            cart = (Cart) cartObj;
        }

        return cart;
    }


    /**
     * Add an item to the user cart
     * @param request Request object received by the servlet
     * @param response Response to be sent
     * @throws ServletException If the request for the GET could not be handled
     * @throws IOException If an input or output error is detected when the servlet handles the GET request
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // TODO : add search filters in the request params

        String idStr = request.getParameter("id");
        int id = -1;
        if(idStr != null && !idStr.trim().isEmpty()) {
            try {
                id = Integer.parseInt(idStr);
            } catch(Exception ignore) {}
        }

        Product product = ProductDAO.getProduct(id);

        if(product == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "The provided product ID doesn't exist");
            return;
        }

        HttpSession session = request.getSession();
        Customer customer = null; // TODO: check if the user is connected and if he is, set this var
        Cart cart;

        if(customer == null) {
            cart = getSessionCart(session);
        } else {
            cart = customer.getCart();
        }

        Set<CartItem> cartItems = cart.getCartItems();

        if(cartItems == null) {
            cartItems = new HashSet<>();
            cart.setCartItems(cartItems);
        }

        request.removeAttribute("isAlreadyInCart");

        for(CartItem item : cartItems) {
            if(item.getProduct().equals(product)) {
                request.setAttribute("isAlreadyInCart", true);
                redirect(request, response, id);
                return;
            }
        }
        // If the product is not already in the cart

        CartItem newItem = new CartItem();
        newItem.setProduct(product);
        newItem.setQuantity(1);

        if(customer == null) {
            cart.getCartItems().add(newItem); // Add to the cart object (not saved in the db)
            request.setAttribute("cart", cart);
        } else {
            CartDAO.addItem(cart, newItem); // Add to the cart of the customer (saved in the db)
            request.removeAttribute("cart");
        }

        redirect(request, response, id);
    }

    public void redirect(HttpServletRequest request, HttpServletResponse response, int productId) throws IOException {
        try {
            RequestDispatcher view = request.getRequestDispatcher("/get-product-page?id="+productId);
            view.forward(request, response);
        } catch(Exception err) {
            // The forward didn't work
            System.err.println(err.getMessage());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
