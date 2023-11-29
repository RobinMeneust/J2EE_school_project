package j2ee_project.dao.profile;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;

import java.util.List;

public class LoyaltyDAO {

    /**
     * get customer loyalty account
     * @param loyaltyAccountId id of the loyalty account we want
     * @return a loyalty account
     */
    public static LoyaltyAccount getLoyaltyAccount(int loyaltyAccountId){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyAccount loyaltyAccount = null;
        try {
            loyaltyAccount = entityManager.createQuery("FROM LoyaltyAccount WHERE id=:loyaltyAccountId", LoyaltyAccount.class).setParameter("loyaltyAccountId", loyaltyAccountId).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return loyaltyAccount;
    }

    /**
     *
     * @return The list of all loyalty levels
     */
    public static List<LoyaltyLevel> getLoyaltyLevels(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        List<LoyaltyLevel> loyaltyLevels = entityManager.createQuery("FROM LoyaltyLevel", LoyaltyLevel.class).getResultList();

        transaction.commit();
        entityManager.close();
        return loyaltyLevels;
    }


    public static LoyaltyLevel getLoyaltyLevel(int idLoyaltyLevel) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyLevel loyaltyLevel = null;
        try {
            loyaltyLevel = entityManager.createQuery("FROM LoyaltyLevel WHERE id=:idLoyaltyLevel", LoyaltyLevel.class).setParameter("idLoyaltyLevel",idLoyaltyLevel).getSingleResult();
        } catch (Exception ignore) {}

        transaction.commit();
        entityManager.close();
        return loyaltyLevel;
    }

    public static void createLevelUsed(int idLoyaltyAccount, int idLoyaltyLevel){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyLevel loyaltyLevelUsed = getLoyaltyLevel(idLoyaltyLevel);
        LoyaltyAccount loyaltyAccount = getLoyaltyAccount(idLoyaltyAccount);
        loyaltyAccount.addLoyaltyLevelUsed(loyaltyLevelUsed);

        transaction.commit();
        entityManager.close();
    }

}
