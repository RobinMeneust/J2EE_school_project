package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

/**
 * Class that interact with the database to edit the Cart table in the database
 *
 * @author Robin Meneust
 */
public class CartDAO {

    public static void addItem(Cart cart, CartItem item) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        cart.getCartItems().add(item);

        transaction.commit();
        entityManager.close();
    }

    public static void addCart(Cart cart) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(cart);

        transaction.commit();
        entityManager.close();
    }
}