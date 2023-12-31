package j2ee_project.dao.user;

import j2ee_project.dao.AddressDAO;
import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Class that interact with the database to edit the Customer table in the database
 *
 * @author Robin Meneust
 */
public class CustomerDAO {
    /**
     * Get the list of all customers
     *
     * @return the list of customers
     */
    public static List<Customer> getCustomers(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Customer> customers = entityManager.createQuery("FROM Customer",Customer.class).getResultList();

        transaction.commit();
        entityManager.close();
        return customers;
    }

    /**
     * Get customer by ID
     *
     * @param customerId the customer's id
     * @return the customer
     */
    public static Customer getCustomer(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = null;
        try {
            customer = entityManager.createQuery("FROM Customer WHERE id=:customerId",Customer.class).setParameter("customerId",customerId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return customer;
    }

    /**
     * Delete customer by ID
     *
     * @param customerId the customer's id
     */
    public static void deleteCustomer(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = entityManager.find(Customer.class,customerId);
        if(customer == null) {
            System.err.println("The customer to be deleted does not exist");
            entityManager.close();
            return;
        }
        entityManager.remove(customer);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Add a customer
     *
     * @param customer the customer
     */
    public static void addCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(customer);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Edit a customer
     *
     * @param customer Customer's new data (but with the same id)
     */
    public static void modifyCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customerToBeEdited = null;
        try {
            customerToBeEdited = entityManager.createQuery("FROM Customer WHERE id = :customerId", Customer.class).setParameter("customerId", customer.getId()).getSingleResult();

            if (customer.getFirstName()!= null && !customer.getFirstName().isEmpty()){
                customerToBeEdited.setFirstName(customer.getFirstName());
            }
            if (customer.getLastName()!= null && !customer.getLastName().isEmpty()){
                customerToBeEdited.setLastName(customer.getLastName());
            }
            if (customer.getPassword()!=null){
                customerToBeEdited.setPassword(customer.getPassword());
            }
            if (customer.getEmail()!= null && !customer.getEmail().isEmpty()){
                customerToBeEdited.setEmail(customer.getEmail());
            }
            if (customer.getPhoneNumber()!= null && !customer.getPhoneNumber().isEmpty()){
                customerToBeEdited.setPhoneNumber(customer.getPhoneNumber());
            }

            Address oldAddress = customerToBeEdited.getAddress();
            Address newAddress;
            boolean isDetached = false;

            if (oldAddress!=null) {
                newAddress = oldAddress;
                List<Customer> nbCustomerWithSameAddress = entityManager.createQuery("FROM Customer WHERE address.id = :addressId", Customer.class)
                        .setParameter("addressId", oldAddress.getId())
                        .getResultList();

                if (nbCustomerWithSameAddress.size() > 1) {
                    // Refs exist so we create a new Address entry
                    entityManager.detach(newAddress);
                    newAddress.setId(0); // Reset the ID to add a new Address with a new auto-generated ID
                    isDetached = true;
                }
            }else{
                newAddress = new Address();
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

            if (oldAddress==null){
                AddressDAO.addAddress(newAddress);
                customerToBeEdited.setAddress(newAddress);
                UserDAO.updateUser(customerToBeEdited);
            }

            if(isDetached) {
                entityManager.persist(newAddress);
            }

            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        entityManager.close();
    }


}
