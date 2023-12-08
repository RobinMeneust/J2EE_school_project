package j2ee_project.model.loyalty;

import j2ee_project.model.Discount;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class LoyaltyAccount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "loyaltyPoints", nullable = false)
    private int loyaltyPoints;
    @Basic
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @ManyToOne
    @JoinColumn(name = "idLoyaltyProgram", referencedColumnName = "id", nullable = false)
    private LoyaltyProgram loyaltyProgram;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "LoyaltyAccountLevelUsed",
            joinColumns = @JoinColumn(name = "idLoyaltyAccount"),
            inverseJoinColumns = @JoinColumn(name = "idLoyaltyLevel")
    )
    private Set<LoyaltyLevel> loyaltyLevelsUsed = new HashSet<>();

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(name = "LoyaltyAccountDiscounts",
            joinColumns = @JoinColumn(name = "idLoyaltyAccount"),
            inverseJoinColumns = @JoinColumn(name = "idDiscount")
    )
    private Set<Discount> availableDiscounts = new HashSet<>();

    public Set<Discount> getAvailableDiscounts() {
        return availableDiscounts;
    }

    public void setAvailableDiscounts(Set<Discount> availableDiscounts) {
        this.availableDiscounts = availableDiscounts;
    }

    public Set<LoyaltyLevel> getLoyaltyLevelsUsed() {
        return loyaltyLevelsUsed;
    }

    public boolean isLevelUsed(LoyaltyLevel level){
        return this.loyaltyLevelsUsed.contains(level);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyAccount that = (LoyaltyAccount) o;

        if (id != that.id) return false;
        if (loyaltyPoints != that.loyaltyPoints) return false;
        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + loyaltyPoints;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    public LoyaltyProgram getLoyaltyProgram() {
        return loyaltyProgram;
    }

    public void setLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
    }

    public void addLoyaltyLevelUsed(LoyaltyLevel level){
        this.loyaltyLevelsUsed.add(level);
        this.getAvailableDiscounts().add(level.getDiscount());
    }

    public void resetLoyaltyLevelUsed(){
        this.loyaltyLevelsUsed = new HashSet<>();
    }
}
