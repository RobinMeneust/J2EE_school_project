package j2ee_project.dao.user;

import j2ee_project.dao.JPAUtil;
import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;

public class PermissionDAO {
    public static Permission getPermission(TypePermission type) {
        EntityManager entityManager = JPAUtil.getInstance().getEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();

        Permission permission = null;
        try {
            permission = entityManager.createQuery("FROM Permission WHERE permission = :permissionType", Permission.class).setParameter("permissionType", type).getSingleResult();
        } catch(Exception ignore) {}


        transaction.commit();
        entityManager.close();
        return permission;
    }
}
