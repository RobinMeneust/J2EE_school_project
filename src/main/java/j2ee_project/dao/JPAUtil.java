package j2ee_project.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.SessionFactory;
import org.hibernate.tool.schema.internal.script.SingleLineSqlScriptExtractor;

/**
 * Singleton class used to get the entityManager
 */
public class JPAUtil
{
	private final EntityManagerFactory entityManagerFactory;
	private static JPAUtil instance = null;

	private JPAUtil() {
		try {
			entityManagerFactory = Persistence.createEntityManagerFactory("j2ee_project");
		} catch (Exception e) {
			System.err.println("EntityManagerFactory creation failed. " + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public static JPAUtil getInstance() {
		if(instance == null) {
			instance = new JPAUtil();
		}
		return instance;
	}


	public EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;
	}

	public EntityManager getEntityManager() {
		return getEntityManagerFactory().createEntityManager();
	}
}