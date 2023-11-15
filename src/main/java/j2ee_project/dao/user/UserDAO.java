package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.User;
import org.hibernate.Session;

public class UserDAO {

    public static void deleteUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    public static void addUser(User user){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Check if an email is in the database
     *
     * @param email the email to check
     * @return the boolean indicating the presence of the email
     */
    public static boolean emailOrPhoneNumberIsInDb(String email, String phoneNumber){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int countEmail = session.createNativeQuery("SELECT COUNT(*) FROM User WHERE email=:email OR phoneNumber=:phoneNumber", Integer.class)
                .setParameter("email", email)
                .setParameter("phoneNumber", phoneNumber)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return countEmail > 0;
    }


    /**
     * Get user from the database with his email
     *
     * @param email the email of the user to get
     * @return the recovered user or null if not
     */
    public static User getUserFromEmail(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        User user;
        user = session.createQuery("FROM User WHERE email=:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
        session.getTransaction().commit();
        session.close();
        return  user;
    }

}
