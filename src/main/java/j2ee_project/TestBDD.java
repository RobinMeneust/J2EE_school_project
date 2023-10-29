package j2ee_project;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.Mail;
import j2ee_project.model.catalog.Category;
import j2ee_project.model.catalog.Product;
import j2ee_project.service.MailManager;
import org.hibernate.Session;

public class TestBDD {

	private Session session;

	public static void main(String[] args) {
		MailManager mailManager = MailManager.getInstance();
		Mail mail = new Mail();
		mail.setSubject("j2ee project test");
		mail.setBody("This is a test mail for a jakarta project");
		mail.setFromAddress("jeewebproject@gmail.com");
		mail.setToAddress("jeewebproject@gmail.com");
		try {
			mailManager.send(mail);
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		new TestBDD().run();
	}

	private void run() {
		session = HibernateUtil.getSessionFactory().openSession();
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

