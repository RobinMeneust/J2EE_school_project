package j2ee_project.service;

import j2ee_project.dao.order.CartDAO;
import j2ee_project.model.order.Cart;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import static j2ee_project.service.AuthService.getCustomer;

public class CartManager {
	public static Cart getSessionCart(HttpSession session) {
		Object cartObj = session.getAttribute("sessionCart");
		Cart cart;

		if(!(cartObj instanceof Cart)) {
			cart = new Cart();
			session.setAttribute("sessionCart", cart);
		} else {
			cart = (Cart) cartObj;
		}

		return cart;
	}

	public static Cart getCart(HttpSession session, Customer customer) {
		if(customer != null /* && AUTHENTICATED && IS IN DATABASE*/) {
			return customer.getCart();
		} else {
			return getSessionCart(session);
		}
	}

	public static Cart getCart(Cart sessionCart, Customer customer) {
		if(customer != null /* && AUTHENTICATED && IS IN DATABASE*/) {
			System.out.println("customer not null and cart = "+customer.getCart()+" items: " + customer.getCart() == null ? "null" : (customer.getCart().getCartItems() == null ? "items null" : "cart not empty, size = "+customer.getCart().getCartItems().size()));
			return customer.getCart();
		} else if(sessionCart != null && sessionCart.getId() <= 0) {
			System.out.println("not null and not customer");
			// If it's not null and not associated to a customer
			return sessionCart;
		}
		System.out.println("null cart");
		return null;
	}

	public static void copySessionCartToCustomer(HttpServletRequest request, Customer customer) {
		HttpSession session = request.getSession();
		if(customer != null) {
			Cart cart = CartManager.getSessionCart(session);

			if(cart != null && cart.getCartItems() != null && !cart.getCartItems().isEmpty()) {
				// Copy the cart
				System.out.println("copy cart");
				CartDAO.updateCart(customer, cart);
			}
			// The session cart and the user cart won't be sync, so it's better to clear the session cart and just use the user cart
			session.removeAttribute("sessionCart");
		}
	}
}
