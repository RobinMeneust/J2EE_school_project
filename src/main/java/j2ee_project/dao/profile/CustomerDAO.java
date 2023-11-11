package j2ee_project.dao.profile;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.Customer;
import org.hibernate.Session;

public class CustomerDAO {

    public static Customer getCustomer(int customerId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.createQuery("FROM Customer WHERE id=:customerId", Customer.class).setParameter("customerId", customerId).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return customer;
    }
}
