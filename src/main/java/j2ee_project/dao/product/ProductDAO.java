package j2ee_project.dao.product;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.catalog.Product;
import org.hibernate.Session;

import java.util.List;

public class ProductDAO {
    public static List<Product> getProducts(int begin, int size) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("FROM Product", Product.class).setFirstResult(begin).setMaxResults(size).getResultList();
        session.getTransaction().commit();
        session.close();

        return products;
    }

    public static Product getProduct(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Product product = session.createQuery("FROM Product WHERE id=:productId", Product.class).setParameter("productId", productId).getSingleResult();
        session.getTransaction().commit();
        session.close();

        return product;
    }

    public static Long getSize() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Long size = (Long) session.createQuery("SELECT COUNT(*) FROM Product", Long.class).uniqueResult();
        session.getTransaction().commit();
        session.close();

        return size;
    }
}
