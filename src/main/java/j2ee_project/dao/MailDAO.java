package j2ee_project.dao;

import j2ee_project.model.Mail;
import org.hibernate.Session;

/**
 * Class that interact with the database to edit the Mail table in the database (add or remove mails)
 *
 * @author Robin Meneust
 */
public class MailDAO {
    /**
     * Add a mail to the database
     * @param mail Mail object containing the mail data (addresses, body...)
     */
    public static void addMail(Mail mail) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(mail);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Remove a mail from the database by using its ID
     * @param mailId ID of the mail to be deleted
     */
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