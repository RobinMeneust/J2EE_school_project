package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

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

    /**
     *
     * @param customer Customer new data (but with the same id)
     */
    public static void modifyCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customerToBeEdited = entityManager.createQuery("FROM Customer WHERE id = :customerId", Customer.class).setParameter("customerId",customer.getId()).getSingleResult();

        //TODO: WE SHOULD NOT EDIT THE ADDRESS BECAUSE IT MIGHT BE USED BY OTHER TABLES
        if (!customer.getFirstName().isEmpty()){
            customerToBeEdited.setFirstName(customer.getFirstName());
        }
        if (!customer.getLastName().isEmpty()){
            customerToBeEdited.setLastName(customer.getLastName());
        }
        if (!customer.getPassword().isEmpty()){
            customerToBeEdited.setPassword(customer.getPassword());
        }
        if (!customer.getEmail().isEmpty()){
            customerToBeEdited.setEmail(customer.getEmail());
        }
        if (!customer.getPhoneNumber().isEmpty()){
            customerToBeEdited.setPhoneNumber(customer.getPhoneNumber());
        }

        Address oldAddress = customerToBeEdited.getAddress();
        Address newAddress = oldAddress;
        boolean isDetached = false;

        long nbAddressRefsInCustomer = entityManager.createQuery("SELECT COUNT(*) FROM Customer WHERE address.id = :addressId", Long.class)
                .setParameter("addressId", oldAddress.getId())
                .getSingleResult();

        long nbAddressRefsInOrders = entityManager.createQuery("SELECT COUNT(*) FROM Orders WHERE address.id = :addressId", Long.class)
                .setParameter("addressId", oldAddress.getId())
                .getSingleResult();

        // Check if any referencing entities exist
        if (nbAddressRefsInCustomer+nbAddressRefsInOrders > 1) {
            // Refs exist so we create a new Address entry
            newAddress = oldAddress;
            entityManager.detach(newAddress);
            newAddress.setId(0); // Reset the ID to add a new Address with a new auto-generated ID
            isDetached = true;
        }

        if (!customer.getAddress().getStreetAddress().isEmpty()){
            newAddress.setStreetAddress(customer.getAddress().getStreetAddress());
        }
        if (!customer.getAddress().getPostalCode().isEmpty()){
            newAddress.setPostalCode(customer.getAddress().getPostalCode());
        }
        if (!customer.getAddress().getCity().isEmpty()){
            newAddress.setCity(customer.getAddress().getCity());
        }
        if (!customer.getAddress().getCountry().isEmpty()){
            newAddress.setCountry(customer.getAddress().getCountry());
        }

        if(isDetached) {
            entityManager.persist(newAddress);
        }

        transaction.commit();
        entityManager.close();
    }
}
