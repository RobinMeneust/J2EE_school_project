package j2ee_project.dao;

import j2ee_project.model.order.Orders;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.*;

public class OrdersDao {

    public static List<Orders> getOrders(int customerId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Orders> orders = (List<Orders>) session.createQuery("FROM Orders WHERE idCustomer=:customerId", Orders.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return orders;
    }
}
