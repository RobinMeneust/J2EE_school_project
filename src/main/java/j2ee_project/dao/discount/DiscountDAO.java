package j2ee_project.dao.discount;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Discount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.List;

public class DiscountDAO {

    public static List<Discount> getDiscounts(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Discount> discounts = null;

        discounts= entityManager.createQuery("FROM Discount ",Discount.class).getResultList();

        transaction.commit();
        entityManager.close();
        return discounts;
    }

    public static Discount getDiscount(int discountId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Discount discount = null;
        try {
            discount = entityManager.createQuery("FROM Discount WHERE id=:discountId",Discount.class).setParameter("discountId",discountId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return discount;
    }

    public static void deleteDiscount(int discountId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Discount discount = entityManager.createQuery("FROM Discount WHERE id=:discountId",Discount.class).setParameter("discountId",discountId).getSingleResult();
            entityManager.remove(discount);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        entityManager.close();
    }

    public static void addDiscount(Discount discount){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(discount);

        transaction.commit();
        entityManager.close();
    }
}
