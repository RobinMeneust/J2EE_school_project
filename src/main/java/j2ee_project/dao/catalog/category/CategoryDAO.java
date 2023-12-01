package j2ee_project.dao.catalog.category;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.catalog.Category;
import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Class that interact with the database to edit the Category table in the database or get data from it
 *
 * @author Robin Meneust, Jérémy Saëlen
 */
public class CategoryDAO {
    /**
     * Get the list of all the categories
     * @return List of categories
     */
    public static List<Category> getCategories(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Category> categories = entityManager.createQuery("FROM Category ",Category.class).getResultList();

        transaction.commit();
        entityManager.close();
        return categories;
    }

    public static Category getCategory(int categoryId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Category category = null;

        try {
            entityManager.createQuery("FROM Category WHERE id=:categoryId", Category.class).setParameter("categoryId", categoryId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return category;
    }

    public static void deleteCategory(int categoryId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Category category = entityManager.createQuery("FROM Category WHERE id=:categoryId", Category.class).setParameter("categoryId", categoryId).getSingleResult();
            entityManager.remove(category);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        transaction.commit();
        entityManager.close();
    }

    public static void addCategory(Category category){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(category);

        transaction.commit();
        entityManager.close();
    }
}
