package j2ee_project.dto;

import j2ee_project.model.user.Permission;
import j2ee_project.model.user.TypePermission;

import java.util.Set;

/**
 * This class is a Data Transfer Object which contain and check Moderator data
 *
 * @author Lucas VELAY
 */
public class ModeratorDTO extends UserDTO{

    private Set<Permission> permissions;

    public ModeratorDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        super(firstName, lastName, email, password, confirmPassword,phoneNumber);
    }

    public Set<Permission> getPermissions(){
        return this.permissions;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    @Override
    public String toString() {
        return "ModeratorDTO{" +
                "firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", email=" + this.getEmail() +
                ", password=" + this.getPassword() +
                ", confirmPassword=" + this.getConfirmPassword() +
                ", phoneNumber=" + this.getPhoneNumber() +
                "permissions=" + this.getPermissions() +
                '}';
    }
}