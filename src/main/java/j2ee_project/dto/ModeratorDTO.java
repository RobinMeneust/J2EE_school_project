package j2ee_project.dto;

import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;

import java.util.Set;

public class ModeratorDTO extends UserDTO{

    private Set<Permission> permissions;

    public ModeratorDTO(String firstName, String lastName, String email, String password, String phoneNumber, Set<Permission> permissions) {
        super(firstName, lastName, email, password, phoneNumber);
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions(){
        return this.permissions;
    }
}