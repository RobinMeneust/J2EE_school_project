package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.User;
import jakarta.persistence.Query;
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

    public static boolean emailIsInDb(String email){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        int countEmail = session.createNativeQuery("SELECT COUNT(*) from User WHERE email=:email", Integer.class)
                .setParameter("email", email)
                .uniqueResult();
        session.getTransaction().commit();
        session.close();
        return countEmail > 0;
    }

}
