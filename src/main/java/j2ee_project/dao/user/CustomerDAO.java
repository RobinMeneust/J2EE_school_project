package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.dao.order.CartDAO;
import j2ee_project.model.order.Cart;
import j2ee_project.model.order.CartItem;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
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

        Customer customer = entityManager.createQuery("FROM Customer WHERE id=:customerId",Customer.class).setParameter("customerId",customerId).getSingleResult();
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
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        // Remove previous cart items
        Cart oldCart = customer.getCart();
        if(oldCart != null) {
            Set<CartItem> oldCartItems = oldCart.getCartItems();
            if (oldCartItems != null) {
                for (CartItem item : oldCartItems) {
                    entityManager.remove(item);
                }
            }
        }


        System.out.println("test customer: "+customer);
        System.out.println("test cart: "+cart);
        System.out.println("test cart items size: "+cart.getCartItems().size());

        cart.setCustomer(customer);
//        CartDAO.addCart(cart);
//        entityManager.detach(customer);
        customer.setCart(cart);
//        entityManager.merge(customer);

        transaction.commit();
        entityManager.close();
    }
}
