package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.Customer;
import org.hibernate.Session;

import java.util.List;

public class CustomerDAO {
    public static List<Customer> getCustomers(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Customer> customers = session.createQuery("FROM Customer",Customer.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return customers;
    }

    public static Customer getCustomer(int customerId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Customer customer = session.createQuery("FROM Customer WHERE id=:customerId",Customer.class).setParameter("customerId",customerId).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return customer;
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
