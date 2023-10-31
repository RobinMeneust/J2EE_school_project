package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.Customer;
import org.hibernate.Session;

import java.util.List;

public class CustomerDAO {
    public static List<Customer> getCustomer(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Customer> customers = session.createQuery("from Customer" /*join User U on Customer.idUser = U.id join Address A on Customer.idAddress = A.id"*/,Customer.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return customers;
    }

    public static void deleteCustomer(Customer customer){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.remove(customer);
        session.getTransaction().commit();
        session.close();
    }

    public static void addCustomer(Customer customer){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(customer);
        session.getTransaction().commit();
        session.close();
    }
}
