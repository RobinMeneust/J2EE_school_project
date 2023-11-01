package j2ee_project.model.user;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "idModerator")
public class Administrator extends Moderator{

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrator administrator = (Administrator) o;

        if (this.getId() != administrator.getId()) return false;

        return true;
    }

}
