package j2ee_project.dao.profile;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.Customer;
import org.hibernate.Session;

public class UserDAO {

    public static Customer getUser(int userId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.createQuery("FROM User WHERE id=:userId", Customer.class).setParameter("userId", userId).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return customer;
    }
}
