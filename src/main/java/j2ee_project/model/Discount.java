package j2ee_project.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Discount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 30)
    private String name;
    @Basic
    @Column(name = "startDate", nullable = true)
    private Date startDate;
    @Basic
    @Column(name = "endDate", nullable = true)
    private Date endDate;
    @Basic
    @Column(name = "discountPercentage", nullable = true)
    private Integer discountPercentage;
    @Basic
    @Column(name = "categoryId", nullable = true)
    private Integer categoryId;
    @Basic
    @Column(name = "idDiscount", nullable = true)
    private Integer idDiscount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
        Discount discount = (Discount) o;
        return id == discount.id && Objects.equals(name, discount.name) && Objects.equals(startDate, discount.startDate) && Objects.equals(endDate, discount.endDate) && Objects.equals(discountPercentage, discount.discountPercentage) && Objects.equals(categoryId, discount.categoryId) && Objects.equals(idDiscount, discount.idDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, startDate, endDate, discountPercentage, categoryId, idDiscount);
    }
}
