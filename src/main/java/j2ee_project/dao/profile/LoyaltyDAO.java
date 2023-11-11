package j2ee_project.dao.profile;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.loyalty.LoyaltyLevel;
import org.hibernate.Session;

import java.util.List;

public class LoyaltyDAO {

    /**
     * get customer loyalty account
     * @param loyaltyAccountId id of the loyalty account we want
     * @return a loyalty account
     */
    public static LoyaltyAccount getLoyaltyAccount(int loyaltyAccountId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        LoyaltyAccount loyaltyAccount = session.createQuery("FROM LoyaltyAccount WHERE id=:loyaltyAccountId", LoyaltyAccount.class).setParameter("loyaltyAccountId", loyaltyAccountId).getSingleResult();
        session.getTransaction().commit();
        session.close();

        return loyaltyAccount;
    }

    /**
     *
     * @return The list of all loyalty levels
     */
    public static List<LoyaltyLevel> getLoyaltyLevels(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        List<LoyaltyLevel> loyaltyLevels = session.createQuery("FROM LoyaltyLevel", LoyaltyLevel.class).getResultList();
        session.getTransaction().commit();
        session.close();

        return loyaltyLevels;
    }



}
