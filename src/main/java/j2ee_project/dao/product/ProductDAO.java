package j2ee_project.dao.product;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.catalog.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.util.*;

public class ProductDAO {
    public static List<Product> getProducts(int begin, int size, String name, String category, String minPrice, String maxPrice) {
        LinkedList<String> listParams = new LinkedList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String queryStr = "FROM Product AS p";
        String queryStrPart2 = "";
        boolean isNotFirstFilter = false;

        if(name != null && !name.trim().isEmpty()) {
            isNotFirstFilter = true;
            queryStrPart2 += "p.name LIKE ?1";
            listParams.add("%"+name+"%");
        }

        if(category != null && !category.trim().isEmpty()) {
            if(isNotFirstFilter) {
                queryStrPart2 += " AND ";
            } else {
                isNotFirstFilter = true;
            }
            queryStrPart2 += "p.category LIKE ?"+(listParams.size()+1);
            listParams.add("%"+category+"%");
        }

        if(minPrice != null && !minPrice.trim().isEmpty() && maxPrice != null && !maxPrice.trim().isEmpty()) {
            if(isNotFirstFilter) {
                queryStrPart2 += " AND ";
            } else {
                isNotFirstFilter = true;
            }
            queryStrPart2 += "(p.unitPrice BETWEEN ?"+(listParams.size()+1)+" AND ?"+(listParams.size()+2)+")";
            listParams.add(minPrice);
            listParams.add(maxPrice);
        }

        if(!queryStrPart2.isEmpty()) {
            queryStr += " WHERE " + queryStrPart2;
        }

        System.out.println("LIST PARAM"+listParams.size());
        for(String str : listParams) {
            System.out.println(str);
        }

        System.out.println(queryStr);
        Query<Product> query = session.createQuery(queryStr, Product.class);
        for(int i=0; i<listParams.size(); i++) {
            query.setParameter(i+1,listParams.get(i));
        }

        System.out.println(query.getQueryString());

        List<Product> products =query.setFirstResult(begin).setMaxResults(size).getResultList();
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
