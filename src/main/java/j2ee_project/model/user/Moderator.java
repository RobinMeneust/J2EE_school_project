package j2ee_project.model.user;

import jakarta.persistence.*;

<<<<<<< HEAD
@Entity
public class Moderator {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "idUser", nullable = false)
    private int idUser;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
=======
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
>>>>>>> feature/models

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Moderator moderator = (Moderator) o;

<<<<<<< HEAD
        if (id != moderator.id) return false;
        if (idUser != moderator.idUser) return false;
=======
        if (this.getId() != moderator.getId()) return false;
>>>>>>> feature/models

        return true;
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        int result = id;
        result = 31 * result + idUser;
        return result;
=======
        return this.getId();
>>>>>>> feature/models
    }
}
