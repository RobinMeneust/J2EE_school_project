package j2ee_project.dao.loyalty;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.Discount;
import j2ee_project.model.loyalty.LoyaltyAccount;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Set;

public class LoyaltyAccountDAO {
    public static void removeDiscount(LoyaltyAccount loyaltyAccount, Discount discount) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        LoyaltyAccount loyaltyAccountDBObj = entityManager.find(LoyaltyAccount.class, loyaltyAccount.getId());
        if(loyaltyAccountDBObj != null) {
            Set<Discount> discounts = loyaltyAccountDBObj.getAvailableDiscounts();
            discounts.remove(discount);
        }

        transaction.commit();
        entityManager.close();
    }
}
