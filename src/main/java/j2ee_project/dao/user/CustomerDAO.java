package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Session;

import java.util.List;
import java.util.Set;

public class CustomerDAO {
    public static List<Customer> getCustomers(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Customer> customers = entityManager.createQuery("FROM Customer",Customer.class).getResultList();

        transaction.commit();
        entityManager.close();
        return customers;
    }

    public static Customer getCustomer(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = entityManager.createQuery("FROM Customer WHERE id=:customerId",Customer.class).setParameter("customerId",customerId).getSingleResult();

        transaction.commit();
        entityManager.close();
        return customer;
    }

    public static void deleteCustomer(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = entityManager.find(Customer.class,customerId);
        if(customer == null) {
            System.err.println("The customer to be deleted does not exist");
            //TODO: it's better to throw a custom Exception in that case (end don't forget to close the entity manager before)
            entityManager.close();
            return;
        }
        entityManager.remove(customer);

        transaction.commit();
        entityManager.close();
    }

    public static void addCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();
        entityManager.close();
    }

    public static void updateCart(Customer customer, Cart cart) {
        System.out.println("UPDATE_CART_BEGIN");
        Cart oldCart = customer.getCart();
        System.out.println("OLD_CART_FETCHED");
        // Remove previous cart items
        removeCartItems(oldCart);
        System.out.println("REMOVED ITEMS DONE");
        removeCart(oldCart);
        System.out.println("REMOVED CART DONE");
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();

        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            System.out.println("test customer: " + customer);
            System.out.println("test cart: " + cart);
            System.out.println("test cart items size: " + cart.getCartItems().size());

            System.out.println("find begin");
            Customer customerDbObject = entityManager.find(Customer.class, customer.getId());
            if (customerDbObject == null) {
                //TODO: return error
                return;
            }
            System.out.println("setCustomer begin ");
            cart.setCustomer(customerDbObject);
            for(CartItem item : cart.getCartItems()) {
                System.out.println("CI id: "+item.getId());
            }

            System.out.println("addCart begin");
            entityManager.persist(cart);
            System.out.println("setCart begin");
            customerDbObject.setCart(cart);
            System.out.println("setCart end");

            transaction.commit();
            System.out.println("UPDATE_CART_END");
        } catch (Exception e) {
            System.err.println("Transactions failed "+e.getMessage());
        }
        finally {
            entityManager.close();
        }
    }

    private static void removeCartItems(Cart cart) {
        if(cart == null) {
            return;
        }
        System.out.println("removeCartItems");
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        System.out.println("find cart ");
        System.out.println(cart);
        System.out.println(cart.getId());
        cart = entityManager.find(Cart.class,cart.getId());

        if(cart != null) {
            System.out.println("get items");
            Set<CartItem> cartItems = cart.getCartItems();
            System.out.println("items got");
            if (cartItems != null) {
                System.out.println("rm item");
                for (CartItem item : cartItems) {
                    entityManager.remove(item);
                }
            }
        }

        transaction.commit();
        entityManager.close();
        System.out.println("removeCartItems end");
    }

    public static void removeCart(Cart cart) {
        if(cart == null) {
            return;
        }
        System.out.println("removeCart start for: "+cart);
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Cart cartDbObject = entityManager.find(Cart.class,cart.getId());
        cartDbObject.getCustomer().setCart(null);
        System.out.println("to be deleted: "+cartDbObject);
        if(cartDbObject != null) {
            entityManager.remove(cartDbObject);
        }

        transaction.commit();
        entityManager.close();
        System.out.println("removeCart end");
    }
}
