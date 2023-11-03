package j2ee_project.model.order;

import jakarta.persistence.*;

@Entity
public class ShippingMethod {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic
    @Column(name = "price", nullable = false, precision = 0)
    private double price;
    @Basic
    @Column(name = "maxDaysTransit", nullable = false)
    private int maxDaysTransit;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMaxDaysTransit() {
        return maxDaysTransit;
    }

    public void setMaxDaysTransit(int maxDaysTransit) {
        this.maxDaysTransit = maxDaysTransit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ShippingMethod that = (ShippingMethod) o;

        if (id != that.id) return false;
        if (Double.compare(price, that.price) != 0) return false;
        if (maxDaysTransit != that.maxDaysTransit) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + maxDaysTransit;
        return result;
    }
}
