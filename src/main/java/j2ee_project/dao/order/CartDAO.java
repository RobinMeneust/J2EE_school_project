package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
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

    public static void removeCart(Cart cart) {
        if(cart == null) {
            return;
        }
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Cart cartDbObject = entityManager.find(Cart.class,cart.getId());
        cartDbObject.getCustomer().setCart(null);
        if(cartDbObject != null) {
            entityManager.remove(cartDbObject);
        }

        transaction.commit();
        entityManager.close();
    }

    public static void updateCart(Customer customer, Cart cart) {
        Cart oldCart = customer.getCart();
        // Remove previous cart items
        CartItemDAO.removeCartItems(oldCart);
        removeCart(oldCart);
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            Customer customerDbObject = entityManager.find(Customer.class, customer.getId());
            if (customerDbObject == null) {
                //TODO: return error
                return;
            }
            cart.setCustomer(customerDbObject);

            entityManager.persist(cart);
            customerDbObject.setCart(cart);

            transaction.commit();
        } catch (Exception e) {
            System.err.println("Transactions failed "+e.getMessage());
        }
        finally {
            entityManager.close();
        }
    }
}