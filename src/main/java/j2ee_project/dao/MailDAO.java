package j2ee_project.dao;

import j2ee_project.model.Mail;
import org.hibernate.Session;


public class MailDAO {
    public static void addMail(Mail mail) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(mail);
        session.getTransaction().commit();
        session.close();
    }

    public static void removeMail(int mailId) {
        Session session= HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        Mail mail = session.createQuery("FROM Mail WHERE id = :mailId", j2ee_project.model.Mail.class).getSingleResult();

        if(mail != null) {
            session.remove(mail);
            session.getTransaction().commit();
        }
        session.close();
    }
}