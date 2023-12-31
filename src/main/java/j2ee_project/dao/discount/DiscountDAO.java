package j2ee_project.dao.discount;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.List;

/**
 * Class that interacts with the database to edit the Discount table in the database or get data from it
 *
 * @author Robin Meneust
 */
public class DiscountDAO {

    /**
     * Get discounts list.
     *
     * @return the list
     */
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

    /**
     * Get discount by id.
     *
     * @param discountId the discount id
     * @return the discount
     */
    public static Discount getDiscount(int discountId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Discount discount = entityManager.find(Discount.class,discountId);

        transaction.commit();
        entityManager.close();
        return discount;
    }

    /**
     * Delete discount by id.
     *
     * @param discountId the discount id
     */
    public static void deleteDiscount(int discountId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();


        Discount discount = entityManager.find(Discount.class,discountId);
        List<Category> categoriesWithDiscount = entityManager.createQuery("FROM Category WHERE discount=:discount", Category.class)
                .setParameter("discount", discount)
                .getResultList();
        for (Category category: categoriesWithDiscount) {
            category.setDiscount(null);
            entityManager.persist(category);
        }
        entityManager.remove(discount);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Add discount.
     *
     * @param discount the discount added
     * @return ID of the new discount
     */
    public static int addDiscount(Discount discount){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(discount);
        entityManager.flush();
        int id = discount.getId();

        transaction.commit();
        entityManager.close();
        return id;
    }


    /**
     * Update discount.
     *
     * @param discount the discount updated that will be merged with the existing one
     */
    public static void updateDiscount(Discount discount) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.merge(discount);

        transaction.commit();
        entityManager.close();
    }
}
