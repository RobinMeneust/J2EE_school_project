package j2ee_project.dao.catalog;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.catalog.Category;
import org.hibernate.Session;

import java.util.List;

/**
 * Class that interact with the database to edit the Category table in the database or get data from it
 *
 * @author Robin Meneust
 */
public class CategoryDAO {
    /**
     * Get the list of all the categories
     * @return List of categories
     */
    public static List<Category> getCategories() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Category> categories = session.createQuery("FROM Category", Category.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return categories;
    }
}
