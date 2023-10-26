package j2ee_project.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "total", nullable = true)
    private Integer total;
    @Basic
    @Column(name = "date", nullable = true)
    private Date date;
    @Basic
    @Column(name = "orderStatus", nullable = true, length = 30)
    private String orderStatus;
    @Basic
    @Column(name = "idCustomer", nullable = true)
    private Integer idCustomer;
    @Basic
    @Column(name = "idShippingMethod", nullable = true)
    private Integer idShippingMethod;
    @Basic
    @Column(name = "idAddress", nullable = true)
    private Integer idAddress;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Integer idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Integer getIdShippingMethod() {
        return idShippingMethod;
    }

    public void setIdShippingMethod(Integer idShippingMethod) {
        this.idShippingMethod = idShippingMethod;
    }

    public Integer getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Integer idAddress) {
        this.idAddress = idAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Orders orders = (Orders) o;
        return id == orders.id && Objects.equals(total, orders.total) && Objects.equals(date, orders.date) && Objects.equals(orderStatus, orders.orderStatus) && Objects.equals(idCustomer, orders.idCustomer) && Objects.equals(idShippingMethod, orders.idShippingMethod) && Objects.equals(idAddress, orders.idAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total, date, orderStatus, idCustomer, idShippingMethod, idAddress);
    }
}
