package j2ee_project.dao.loyalty;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.loyalty.LoyaltyLevel;
import j2ee_project.model.loyalty.LoyaltyProgram;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class LoyaltyProgramDAO {

    public static LoyaltyProgram getLoyaltyProgram(){
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyProgram loyaltyProgram = entityManager.find(LoyaltyProgram.class, 1);

        transaction.commit();
        entityManager.close();
        return loyaltyProgram;
    }

}
