package j2ee_project.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class LoyaltyProgram {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "durationNbDays", nullable = true)
    private Integer durationNbDays;
    @Basic
    @Column(name = "idLoyaltyAccount", nullable = true)
    private Integer idLoyaltyAccount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getDurationNbDays() {
        return durationNbDays;
    }

    public void setDurationNbDays(Integer durationNbDays) {
        this.durationNbDays = durationNbDays;
    }

    public Integer getIdLoyaltyAccount() {
        return idLoyaltyAccount;
    }

    public void setIdLoyaltyAccount(Integer idLoyaltyAccount) {
        this.idLoyaltyAccount = idLoyaltyAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoyaltyProgram that = (LoyaltyProgram) o;
        return id == that.id && Objects.equals(durationNbDays, that.durationNbDays) && Objects.equals(idLoyaltyAccount, that.idLoyaltyAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, durationNbDays, idLoyaltyAccount);
    }
}
