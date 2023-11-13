package j2ee_project.dao;

import j2ee_project.model.Address;
import org.hibernate.Session;

public class AddressDAO {
    public static void addAddress(Address newAddress) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.persist(newAddress);
        session.getTransaction().commit();
        session.close();
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
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Address addressFetched = null;
        try {
            addressFetched = session.createQuery("FROM Address WHERE streetAddress=:streetAddress AND postalCode =:postalCode AND city=:city AND country=:country", Address.class)
                    .setParameter("streetAddress", address.getStreetAddress())
                    .setParameter("postalCode",address.getPostalCode())
                    .setParameter("city",address.getCity())
                    .setParameter("country",address.getCountry())
                    .getSingleResult();
            session.getTransaction().commit();
        } catch(Exception ignore) {}
        session.close();
        return addressFetched;
    }
}
