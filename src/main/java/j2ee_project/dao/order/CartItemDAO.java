package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.order.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

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
}
