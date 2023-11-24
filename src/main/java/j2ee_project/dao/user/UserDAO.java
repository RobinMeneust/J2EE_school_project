package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
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
        Session session = JPAUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
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

}
