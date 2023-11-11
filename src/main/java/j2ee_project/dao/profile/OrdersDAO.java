package j2ee_project.dao.profile;

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
}
