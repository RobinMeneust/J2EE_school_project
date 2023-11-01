package j2ee_project.dao.product;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.catalog.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.util.*;

public class ProductDAO {
    public static List<Product> getProducts(int begin, int size, HashMap<String,String> textMatchFilters, HashMap<String, String[]> rangeFilters) {
        HashSet<String> allowedKeys = new HashSet<>();
        allowedKeys.add("name");
        allowedKeys.add("category");
        allowedKeys.add("minPrice");
        allowedKeys.add("maxPrice");

        LinkedList<String> listParams = new LinkedList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String queryStr = "FROM Product AS prdt";

        if(!textMatchFilters.isEmpty() || !rangeFilters.isEmpty()) {
            queryStr += " WHERE ";
        }

        boolean isNotFirstFilter = false;

        for(Map.Entry<String,String> entry : textMatchFilters.entrySet()) {
            if(isNotFirstFilter) {
                queryStr += " AND ";
            } else {
                isNotFirstFilter = true;
            }
            if(!entry.getKey().trim().isEmpty() && allowedKeys.contains(entry.getKey()) && !entry.getValue().trim().isEmpty()) {
                queryStr += "prdt." + entry.getKey() + " LIKE \"%:p" + listParams.size() + "%\"";
                listParams.add(entry.getValue());
            }
        }

        for(Map.Entry<String,String[]> entry : rangeFilters.entrySet()) {
            if(isNotFirstFilter) {
                queryStr += " AND ";
            } else {
                isNotFirstFilter = true;
            }
            if(!entry.getKey().trim().isEmpty() && allowedKeys.contains(entry.getKey()) && entry.getValue().length == 2) {
                queryStr += "(prdt."+entry.getKey() + " BETWEEN :p" + listParams.size() + " AND :p" + listParams.size()+")" ;
                listParams.add(entry.getValue()[0]);
                listParams.add(entry.getValue()[1]);
            }
        }

        System.out.println("LIST PARAM"+listParams.size());
        for(String str : listParams) {
            System.out.println(str);
        }

        System.out.println(queryStr);
        Query<Product> query = session.createQuery(queryStr, Product.class);
        for(int i=0; i<listParams.size(); i++) {
            query.setParameter("p"+i,listParams.get(i));
        }

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
