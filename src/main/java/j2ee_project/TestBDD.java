package j2ee_project;

import j2ee_project.model.Category;
import j2ee_project.model.Product;
import org.hibernate.Session;

public class TestBDD {

	private Session session;

	public static void main(String[] args) {

		new TestBDD().run();
	}

	private void run() {
		session= HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		Category category1 = save(new Category("cat1", "test cat"));
		Product product1 = save(new Product("product 1", 2, 1.0f, "test product", "", 2.0f, category1));

		session.getTransaction().commit();
		session.close();
	}

	private<T>  T save(T o) {
		session.save(o);
		return o;
	}

}

