package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.dao.user.CustomerDAO;
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
        int itemId = CartItemDAO.newItem(item);
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        CartItem itemDBObj = entityManager.find(CartItem.class,itemId);
        itemDBObj.setCart(cart);
        cart.getCartItems().add(itemDBObj);

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

    /**
     * Update the cart only if the customer's cart is empty
     * @param customer
     * @param cart
     */
    public static void updateCart(Customer customer, Cart cart) {
        Cart oldCart = customer.getCart();
        if(oldCart != null && oldCart.getCartItems() != null && !oldCart.getCartItems().isEmpty()) {
            // Cancelled : The cart should not be updated since the old one in still active
            return;
        }

        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();

        try {
            CartDAO.removeCart(oldCart); // Remove the old cart to persist a new one
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();

            Customer customerDbObject = entityManager.find(Customer.class, customer.getId());
            if (customerDbObject == null) {
                //TODO: return error
                return;
            }
            cart.setCustomer(customerDbObject);
            cart.setId(0); // To persist a new cart

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

    public static Cart getCartFromCustomerId(int id) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Cart cart = entityManager.createQuery("FROM Cart WHERE customer.id = :customerId",Cart.class).setParameter("customerId",id).getSingleResult();

        transaction.commit();
        entityManager.close();
        return cart;
    }
}