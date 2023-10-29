package j2ee_project.dao;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil
{
	private static final SessionFactory sessionFactory;
	static
	{
		final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
		try {
			EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
			sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		}
		catch (Exception e) {
			StandardServiceRegistryBuilder.destroy(registry);
			System.err.println("Initial SessionFactory creation failed." + e);
			throw new ExceptionInInitializerError(e);
		}
	}
	public static SessionFactory getSessionFactory()
	{
		return sessionFactory;
	}
}