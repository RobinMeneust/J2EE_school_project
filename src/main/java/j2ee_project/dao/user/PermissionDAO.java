package j2ee_project.dao.user;

import j2ee_project.dao.HibernateUtil;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import org.hibernate.Session;

public class PermissionDAO {

    public static Permission getPermission(TypePermission typePermission){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Permission permission = session.createQuery("FROM Permission WHERE permission=:typePermission", Permission.class).setParameter("typePermission",typePermission).getSingleResult();
        session.getTransaction().commit();
        session.close();
        return permission;
    }
}
