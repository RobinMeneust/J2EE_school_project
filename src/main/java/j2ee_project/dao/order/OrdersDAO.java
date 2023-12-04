package j2ee_project.dao.order;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.order.OrderStatus;
import j2ee_project.model.order.Orders;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.*;

public class OrdersDAO {

    public static List<Orders> getOrders(int customerId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Orders> orders = entityManager.createQuery("FROM Orders WHERE customer=:customerId", Orders.class).setParameter("customerId", customerId).getResultList();

        transaction.commit();
        entityManager.close();
        return orders;
    }

    public static void addOrder(Orders newOrder) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(newOrder);

        transaction.commit();
        entityManager.close();
    }

    public static Orders getOrder(String orderId) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Orders order = entityManager.find(Orders.class,orderId);

        transaction.commit();
        entityManager.close();
        return order;
    }

    public static void setStatus(Orders order, OrderStatus orderStatus) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Orders orderDBObj = entityManager.find(Orders.class,order.getId());
        if(orderDBObj != null) {
            orderDBObj.setOrderStatus(orderStatus);
            transaction.commit();
        }

        entityManager.close();
    }
}
