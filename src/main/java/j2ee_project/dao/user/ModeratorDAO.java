package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.Moderator;
import org.hibernate.Session;

import java.util.List;

public class ModeratorDAO {

    public static List<Moderator> getModerators(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<Moderator> moderators = session.createQuery("FROM Moderator ",Moderator.class).getResultList();
        session.getTransaction().commit();
        session.close();
        return moderators;
    }

    public static Moderator getModerator(int moderatorId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Moderator moderator = session.createQuery("FROM Moderator WHERE id=:moderatorId", Moderator.class).setParameter("moderatorId",moderatorId).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return moderator;
    }

    public static void deleteModerator(int moderatorId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Moderator moderator = session.createQuery("FROM Moderator WHERE id=:moderatorId",Moderator.class).setParameter("moderatorId",moderatorId).getSingleResult();
        session.remove(moderator);
        session.getTransaction().commit();
        session.close();
    }

    public static void addModerator(Moderator moderator){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.save(moderator);
        session.getTransaction().commit();
        session.close();
    }
}
