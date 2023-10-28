package j2ee_project.model.loyalty;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class LoyaltyAccount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "loyaltyPoints", nullable = true)
    private Integer loyaltyPoints;
    @Basic
    @Column(name = "startDate", nullable = true)
    private Date startDate;
    @Basic
    @Column(name = "description", nullable = true, length = 300)
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(Integer loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoyaltyAccount that = (LoyaltyAccount) o;
        return id == that.id && Objects.equals(loyaltyPoints, that.loyaltyPoints) && Objects.equals(startDate, that.startDate) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loyaltyPoints, startDate, description);
    }
}
