package j2ee_project.dao;

import j2ee_project.model.Address;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

public class AddressDAO {
    public static void addAddress(Address newAddress) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(newAddress);

        transaction.commit();
        entityManager.close();
    }

    public static Address addAddressIfNotExists(Address newAddress) {
        Address address = getAddress(newAddress);
        if(address == null) {
            addAddress(newAddress);
            address = getAddress(newAddress);
        }
        return address;
    }

    public static Address getAddress(Address address) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Address addressFetched = null;
        try {
            addressFetched = entityManager.createQuery("FROM Address WHERE streetAddress=:streetAddress AND postalCode =:postalCode AND city=:city AND country=:country", Address.class)
                    .setParameter("streetAddress", address.getStreetAddress())
                    .setParameter("postalCode",address.getPostalCode())
                    .setParameter("city",address.getCity())
                    .setParameter("country",address.getCountry())
                    .getSingleResult();
            transaction.commit();
        } catch(Exception ignore) {}

        entityManager.close();
        return addressFetched;
    }
}
