package j2ee_project.service;

import j2ee_project.model.order.Cart;
import j2ee_project.model.user.Customer;
import jakarta.servlet.http.HttpSession;

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
			return customer.getCart();
		} else if(sessionCart != null && sessionCart.getId() <= 0) {
			// If it's not null and not associated to a customer
			return sessionCart;
		}
		return null;
	}
}
