package j2ee_project.dao.loyalty;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.loyalty.LoyaltyLevel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class LoyaltyLevelDAO {
    public static LoyaltyLevel getLoyaltyLevel(int id) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyLevel loyaltyLevel = entityManager.find(LoyaltyLevel.class,id);

        transaction.commit();
        entityManager.close();
        return loyaltyLevel;
    }
}
