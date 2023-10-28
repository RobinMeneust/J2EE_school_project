package j2ee_project.model.order;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ShippingMethod {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 50)
    private String name;
    @Basic
    @Column(name = "price", nullable = true, precision = 0)
    private Double price;
    @Basic
    @Column(name = "maxDaysTransit", nullable = true)
    private Integer maxDaysTransit;

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getMaxDaysTransit() {
        return maxDaysTransit;
    }

    public void setMaxDaysTransit(Integer maxDaysTransit) {
        this.maxDaysTransit = maxDaysTransit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShippingMethod that = (ShippingMethod) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(price, that.price) && Objects.equals(maxDaysTransit, that.maxDaysTransit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, maxDaysTransit);
    }
}
