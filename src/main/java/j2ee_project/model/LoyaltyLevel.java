package j2ee_project.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class LoyaltyLevel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "requiredPoints", nullable = true)
    private Integer requiredPoints;
    @Basic
    @Column(name = "idLoyaltyProgram", nullable = true)
    private Integer idLoyaltyProgram;
    @Basic
    @Column(name = "idDiscount", nullable = true)
    private Integer idDiscount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getRequiredPoints() {
        return requiredPoints;
    }

    public void setRequiredPoints(Integer requiredPoints) {
        this.requiredPoints = requiredPoints;
    }

    public Integer getIdLoyaltyProgram() {
        return idLoyaltyProgram;
    }

    public void setIdLoyaltyProgram(Integer idLoyaltyProgram) {
        this.idLoyaltyProgram = idLoyaltyProgram;
    }

    public Integer getIdDiscount() {
        return idDiscount;
    }

    public void setIdDiscount(Integer idDiscount) {
        this.idDiscount = idDiscount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoyaltyLevel that = (LoyaltyLevel) o;
        return id == that.id && Objects.equals(requiredPoints, that.requiredPoints) && Objects.equals(idLoyaltyProgram, that.idLoyaltyProgram) && Objects.equals(idDiscount, that.idDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, requiredPoints, idLoyaltyProgram, idDiscount);
    }
}
