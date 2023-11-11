package j2ee_project.model.user;

import j2ee_project.dto.ModeratorDTO;
import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "idModerator")
public class Administrator extends Moderator{
    public Administrator(ModeratorDTO moderatorDTO) {
        super(moderatorDTO);
    }

    public Administrator() {
        super();
    }

/*    public Administrator(){
        this.addPermission(new Permission(TypePermission.CAN_CREATE_CUSTOMER));
        this.addPermission(new Permission(TypePermission.CAN_DELETE_CUSTOMER));
        this.addPermission(new Permission(TypePermission.CAN_CREATE_DISCOUNT));
        this.addPermission(new Permission(TypePermission.CAN_MANAGE_LOYALTY));
        this.addPermission(new Permission(TypePermission.CAN_MANAGE_ORDER));
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Administrator administrator = (Administrator) o;

        if (this.getId() != administrator.getId()) return false;

        return true;
    }

}