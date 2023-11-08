package j2ee_project.dao.catalog.category;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.Discount;
import j2ee_project.model.catalog.Category;
import org.hibernate.Session;

import java.util.List;

public class CategoryDAO {

    public static List<Category> getCategories(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Category> categories = session.createQuery("FROM Category ",Category.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return categories;
    }

    public static Category getCategory(int categoryId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Category category = session.createQuery("FROM Category WHERE id=:categoryId",Category.class).setParameter("categoryId",categoryId).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return category;
    }

    public static void deleteCategory(int categoryId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Category category = session.createQuery("FROM Category WHERE id=:categoryId",Category.class).setParameter("categoryId",categoryId).getSingleResult();
        session.remove(category);
        session.getTransaction().commit();
        session.close();
    }

    public static void addCategory(Category category){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(category);
        session.getTransaction().commit();
        session.close();
    }
}
