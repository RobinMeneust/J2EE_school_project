package j2ee_project.dao.order;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import org.hibernate.Session;

/**
 * Class that interact with the database to edit the Mail table in the database (add or remove mails)
 *
 * @author Robin Meneust
 */
public class CartDAO {

    public static void addItem(Cart cart, CartItem item) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.evict(cart);
        cart.getCartItems().add(item);
        session.merge(cart);

        session.getTransaction().commit();
        session.close();
    }

    public static void editItemQuantity(CartItem cartItem, int quantity) {
        // TODO
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        if(quantity<=0) {
            session.remove(cartItem);
        } else {
            session.evict(cartItem);
            cartItem.setQuantity(quantity);
            session.merge(cartItem);
        }

        session.getTransaction().commit();
        session.close();
    }
}