package j2ee_project.dao.catalog.category;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.user.Customer;
import org.hibernate.Session;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

/**
 * Class that interacts with the database to edit the Category table in the database or get data from it
 *
 * @author Robin Meneust, Jérémy Saëlen
 */
public class CategoryDAO {
    /**
     * Get the list of all the categories
     *
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

    /**
     * Get category from its id
     *
     * @param categoryId the category id
     * @return the category
     */
    public static Category getCategory(int categoryId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Category category = entityManager.find(Category.class,categoryId);

        transaction.commit();
        entityManager.close();
        return category;
    }

    /**
     * Delete category.
     *
     * @param categoryId the category id
     */
    public static void deleteCategory(int categoryId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Category category = entityManager.find(Category.class,categoryId);
        entityManager.remove(category);

        transaction.commit();
        entityManager.close();
    }

    /**
     * Add category.
     *
     * @param category the category
     */
    public static void addCategory(Category category){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(category);

        transaction.commit();
        entityManager.close();
    }
}
