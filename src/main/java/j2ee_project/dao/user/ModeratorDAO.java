package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.user.Moderator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import org.hibernate.Session;

import java.util.List;

public class ModeratorDAO {

    public static List<Moderator> getModerators(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<Moderator> moderators = entityManager.createQuery("FROM Moderator ",Moderator.class).getResultList();

        transaction.commit();
        entityManager.close();
        return moderators;
    }

    public static Moderator getModerator(int moderatorId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Moderator moderator = null;
        try {
            moderator = entityManager.createQuery("FROM Moderator WHERE id=:moderatorId", Moderator.class).setParameter("moderatorId", moderatorId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return moderator;
    }

    public static void deleteModerator(int moderatorId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        try {
            Moderator moderator = entityManager.createQuery("FROM Moderator WHERE id=:moderatorId", Moderator.class).setParameter("moderatorId", moderatorId).getSingleResult();
            entityManager.remove(moderator);
            transaction.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        entityManager.close();
    }

    public static void addModerator(Moderator moderator){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        entityManager.persist(moderator);

        transaction.commit();
        entityManager.close();
    }
}
