package j2ee_project.dao.order;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.order.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.*;

public class OrdersDAO {

    public static List<Orders> getOrders(int customerId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Orders> orders = session.createQuery("FROM Orders WHERE customer=:customerId", Orders.class).setParameter("customerId", customerId).getResultList();
        session.getTransaction().commit();
        session.close();
        return orders;
    }

    public static void addOrder(Orders newOrder) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(newOrder);
        session.getTransaction().commit();
        session.close();
    }

    public static Orders getOrder(String orderId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Orders order;
        try {
            order = session.createQuery("FROM Orders WHERE id=:orderId", Orders.class).setParameter("orderId", orderId).getSingleResult();
        } catch(Exception e) {
            order = null;
        }
        session.getTransaction().commit();
        session.close();
        return order;
    }
}
