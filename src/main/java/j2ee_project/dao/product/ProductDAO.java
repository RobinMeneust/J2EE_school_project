package j2ee_project.dao.product;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.catalog.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;


import java.util.*;

/**
 * Class that interact with the database to edit the Product table in the database or get data from it
 *
 * @author Robin Meneust
 */
public class ProductDAO {
    /**
     * Get a list of products that match the given filters
     * @param begin Index of the first element (e.g. begin=4 will ignore the 3 first products)
     * @param size Max number of elements returned
     * @param name The products' name must match with this name (it's case-insensitive, and it can be just a part of a searched word: e.g. 'Ch' will return 'chess' products)
     * @param category The products' category must match with it (exactly like the name filter)
     * @param minPrice The products' price must be greater or equal to this value
     * @param maxPrice The products' price must be lesser or equal to this value
     * @return List of products that match with all of those filters
     */
    public static List<Product> getProducts(int begin, int size, String name, String category, String minPrice, String maxPrice) {
        LinkedList<String> listParams = new LinkedList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        String queryStr = "FROM Product AS p";
        String queryStrPart2 = "";
        boolean isNotFirstFilter = false;

        // Check the filters and add them to the query if they are valid

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

        // Add the filters if there is at least one that is valid
        if(!queryStrPart2.isEmpty()) {
            queryStr += " WHERE " + queryStrPart2;
        }


        Query<Product> query = session.createQuery(queryStr, Product.class);
        for(int i=0; i<listParams.size(); i++) {
            query.setParameter(i+1,listParams.get(i));
        }

        List<Product> products =query.setFirstResult(begin).setMaxResults(size).getResultList();
        session.getTransaction().commit();
        session.close();

        return products;
    }

    /**
     * Get a product from its ID
     * @param productId ID of the searched product
     * @return Product whose ID matched with the one provided
     */
    public static Product getProduct(int productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Product product = session.createQuery("FROM Product WHERE id=:productId", Product.class).setParameter("productId", productId).getSingleResult();
        session.getTransaction().commit();
        session.close();

        return product;
    }

    /**
     * Get the total number of products
     * @return Number of products
     */
    public static Long getSize() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Long size = (Long) session.createQuery("SELECT COUNT(*) FROM Product", Long.class).uniqueResult();
        session.getTransaction().commit();
        session.close();

        return size;
    }
}
