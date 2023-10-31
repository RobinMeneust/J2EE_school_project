package j2ee_project.dao.product;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.catalog.Product;
import j2ee_project.model.user.Administrator;
import org.hibernate.Internal;
import org.hibernate.Session;

import java.util.List;

public class ProductDAO {
    public static List<Product> getProducts() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Product> products = session.createQuery("FROM Product", Product.class).getResultList();
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
}
