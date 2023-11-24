package j2ee_project.dao.profile;

import j2ee_project.dao.AddressDAO;
import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.query.MutationQuery;

public class UserDAO {

    public static Customer getUser(int userId) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Customer customer = entityManager.createQuery("FROM User WHERE id=:userId", Customer.class).setParameter("userId", userId).getSingleResult();

        transaction.commit();
        entityManager.close();
        return customer;
    }

    public static void modifyCustomer(Customer customer){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        String userQueryStr = getUserUpdateQueryString(customer);
        String addressQueryString = getAddressUpdateQueryString(customer);
        MutationQuery userQuery = entityManager.createMutationQuery(userQueryStr).setParameter("userId",customer.getId());
        MutationQuery addressQuery = entityManager.createMutationQuery(addressQueryString).setParameter("userId",customer.getAddress().getId());

        if (!customer.getFirstName().isEmpty()){
            userQuery.setParameter("customerFirstName",customer.getFirstName());
        }
        if (!customer.getLastName().isEmpty()){
            userQuery.setParameter("customerLastName",customer.getLastName());
        }
        if (!customer.getPassword().isEmpty()){
            userQuery.setParameter("customerPassword",customer.getPassword());
        }
        if (!customer.getEmail().isEmpty()){
            userQuery.setParameter("customerEmail",customer.getEmail());
        }
        if (!customer.getPhoneNumber().isEmpty()){
            userQuery.setParameter("customerPhoneNumber",customer.getPhoneNumber());
        }
        if (!customer.getAddress().getStreetAddress().isEmpty()){
            addressQuery.setParameter("customerStreetAddress",customer.getAddress().getStreetAddress());
        }
        if (!customer.getAddress().getPostalCode().isEmpty()){
            addressQuery.setParameter("customerPostalCode",customer.getAddress().getPostalCode());
        }
        if (!customer.getAddress().getCity().isEmpty()){
            addressQuery.setParameter("customerCity",customer.getAddress().getCity());
        }
        if (!customer.getAddress().getCountry().isEmpty()){
            addressQuery.setParameter("customerCountry",customer.getAddress().getCountry());
        }
        Customer customer1 = getUser(customer.getId());
        if (customer1.getAddress()==null){
            Address addressIfNotExist = customer.getAddress();
            addressIfNotExist = AddressDAO.addAddressIfNotExists(addressIfNotExist);
            MutationQuery customerQuery = entityManager.createMutationQuery("UPDATE Customer SET address=:address WHERE id=:idCustomer").setParameter("address",addressIfNotExist).setParameter("idCustomer",customer.getId());
            customerQuery.executeUpdate();
        }
        userQuery.executeUpdate();
        addressQuery.executeUpdate();

        transaction.commit();
        entityManager.close();
    }

    public static String getUserUpdateQueryString(Customer customer) {
        String queryStr ="UPDATE User SET";

        if (!customer.getFirstName().isEmpty()){
            queryStr += " firstName=:customerFirstName";
        }
        if (!customer.getLastName().isEmpty()){
            queryStr += ", lastName=:customerLastName";
        }
        if (!customer.getPassword().isEmpty()){
            queryStr += ", password=:customerPassword";
        }
        if (!customer.getEmail().isEmpty()){
            queryStr += ", email=:customerEmail";
        }
        if (!customer.getPhoneNumber().isEmpty()){
            queryStr += ", phoneNumber=:customerPhoneNumber";
        }
        queryStr+=" WHERE id=:userId";
        return queryStr;
    }

    public static String getAddressUpdateQueryString(Customer customer) {
        String queryStr ="UPDATE Address SET";

        if (!customer.getAddress().getStreetAddress().isEmpty()){
            queryStr += " streetAddress=:customerStreetAddress";
        }
        if (!customer.getAddress().getPostalCode().isEmpty()){
            queryStr += ", postalCode=:customerPostalCode";
        }
        if (!customer.getAddress().getCity().isEmpty()){
            queryStr += ", city=:customerCity";
        }
        if (!customer.getAddress().getCountry().isEmpty()){
            queryStr += ", country=:customerCountry";
        }
        queryStr+=" WHERE id=:userId";
        return queryStr;
    }
}
