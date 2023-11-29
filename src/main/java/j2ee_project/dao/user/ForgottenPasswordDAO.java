package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.Mail;
import j2ee_project.model.user.ForgottenPassword;
import j2ee_project.model.user.User;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

/**
 * Class that interact with the database to edit the ForgottenPassword table in the database (add or remove mails)
 *
 * @author Lucas Velay
 */
public class ForgottenPasswordDAO {

    public static void addForgottenPassword(ForgottenPassword forgottenPassword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(forgottenPassword);
        session.getTransaction().commit();
        session.close();
    }

    public static void removeForgottenPassword(ForgottenPassword forgottenPassword) {
        if(forgottenPassword != null) {
            Session session= HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.remove(forgottenPassword);
            session.getTransaction().commit();
            session.close();
        }
    }

    public static ForgottenPassword getForgottenPasswordFromToken(String token){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        ForgottenPassword forgottenPassword;
        try{
            forgottenPassword = session.createQuery("FROM ForgottenPassword WHERE token=:token", ForgottenPassword.class)
                    .setParameter("token", token)
                    .getSingleResult();
        }catch (Exception exception){
            forgottenPassword = null;
        }
        session.getTransaction().commit();
        session.close();
        return  forgottenPassword;
    }

}
