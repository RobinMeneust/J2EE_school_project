package j2ee_project.dao;

import j2ee_project.model.user.Customer;
import org.hibernate.Session;

public class UserDAO {

    public static void addCustomer(Customer customer){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
    }

}
