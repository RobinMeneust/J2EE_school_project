package j2ee_project.model.loyalty;

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
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @ManyToOne
    @JoinColumn(name = "idLoyaltyProgram", referencedColumnName = "id", nullable = false)
    private LoyaltyProgram loyaltyProgram;

    @ManyToMany
    @JoinTable(name = "LoyaltyAccountLevelUsed",
            joinColumns = @JoinColumn(name = "idLoyaltyAccount"),
            inverseJoinColumns = @JoinColumn(name = "idLoyaltyLevel")
    )
    private Set<LoyaltyLevel> loyaltyLevelsUsed = new HashSet<>();

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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LoyaltyAccount that = (LoyaltyAccount) o;

        if (id != that.id) return false;
        if (loyaltyPoints != that.loyaltyPoints) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + loyaltyPoints;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
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
    }

    public void resetLoyaltyLevelUsed(){
        this.loyaltyLevelsUsed = new HashSet<>();
    }
}
