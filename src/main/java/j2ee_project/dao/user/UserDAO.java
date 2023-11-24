package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

/**
 * This class is utility class for the methods that interacts with the database.
 *
 * @author Lucas VELAY
 */
public class UserDAO {

    /**
     * Delete a user from the database
     *
     * @param user the user to delete
     */
    public static void deleteUser(User user){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.remove(user);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Add a user in the database
     *
     * @param user the user to add
     */
    public static void addUser(User user){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(user);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Check if an email and a phone number are in the database
     *
     * @param email the email to check
     * @return the boolean indicating the presence of the email
     */
    public static boolean emailOrPhoneNumberIsInDb(String email, String phoneNumber){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        int countEmail = entityManager.createQuery("SELECT COUNT(*) FROM User WHERE email=:email OR phoneNumber=:phoneNumber", Integer.class)
                .setParameter("email", email)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();

        transaction.commit();
        entityManager.close();
        return countEmail > 0;
    }


    /**
     * Get user from the database with his email
     *
     * @param email the email of the user to get
     * @return the recovered user or null if not
     */
    public static User getUserFromEmail(String email){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        User user;
        user = entityManager.createQuery("FROM User WHERE email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();

        transaction.commit();
        entityManager.close();
        return  user;
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

        //TODO: WE SHOULD NOT EDIT THE ADDRESS BECAUSE IT MIGHT BE USED BY OTHER
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

        Address newAddress = customerToBeEdited.getAddress();

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

        transaction.commit();
        entityManager.close();
    }
}
