package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.Set;

/**
 * Class that interact with the database to edit the CartItem table in the database
 *
 * @author Robin Meneust
 */
public class CartItemDAO {

    public static void editItemQuantity(CartItem cartItem, int quantity) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        if(quantity<=0) {
            entityManager.remove(cartItem);
        } else {
            cartItem.setQuantity(quantity);
        }

        transaction.commit();
        entityManager.close();
    }


    public static void removeCartItems(Cart cart) {
        if(cart == null) {
            return;
        }
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        cart = entityManager.find(Cart.class,cart.getId());

        if(cart != null) {
            Set<CartItem> cartItems = cart.getCartItems();
            if (cartItems != null) {
                for (CartItem item : cartItems) {
                    entityManager.remove(item);
                }
            }
        }

        transaction.commit();
        entityManager.close();
    }
}
