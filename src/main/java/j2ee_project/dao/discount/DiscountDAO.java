package j2ee_project.dao.discount;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.Discount;
import org.hibernate.Session;

import java.util.List;

public class DiscountDAO {

    public static List<Discount> getDiscounts(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Discount> discounts = session.createQuery("FROM Discount ",Discount.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return discounts;
    }

    public static Discount getDiscount(int discountId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Discount discount = session.createQuery("FROM Discount WHERE id=:discountId",Discount.class).setParameter("discountId",discountId).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return discount;
    }

    public static void deleteDiscount(int discountId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Discount discount = session.createQuery("FROM Discount WHERE id=:discountId",Discount.class).setParameter("discountId",discountId).getSingleResult();
        session.remove(discount);
        session.getTransaction().commit();
        session.close();
    }

    public static void addDiscount(Discount discount){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(discount);
        session.getTransaction().commit();
        session.close();
    }
}
