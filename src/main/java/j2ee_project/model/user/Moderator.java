package j2ee_project.model.user;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "idUser")
public class Moderator extends User{

    @ManyToMany
    @JoinTable(name = "ModeratorPermission",
            joinColumns = @JoinColumn(name = "idModerator"),
            inverseJoinColumns = @JoinColumn(name = "idPermission")
    )
    private Set<Permission> permissions = new HashSet<>();

    public Moderator(){
        super();
    }


    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void addPermission(Permission permission){
        this.permissions.add(permission);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Moderator moderator = (Moderator) o;

        if (this.getId() != moderator.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }
}