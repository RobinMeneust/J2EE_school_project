package j2ee_project.model.order;

import j2ee_project.model.Address;
import j2ee_project.model.user.Customer;
import jakarta.persistence.*;

import java.sql.Date;
import java.util.Set;

@Entity
public class Orders {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "total", nullable = false)
    private float total;
    @Basic
    @Column(name = "date", nullable = false)
    private Date date;
    @Basic
    @Column(name = "orderStatus", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @OneToMany(mappedBy = "order", fetch=FetchType.EAGER)
    private Set<CartItem> cartItems;
    @ManyToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idUser", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "id", nullable = false)
    private Address address;

    public Orders() { }

    public Orders(float total, Date date, Set<CartItem> cartItems, Customer customer, Address address) {
        this.total = total;
        this.date = date;
        this.cartItems = cartItems;
        this.customer = customer;
        this.address = address;
        this.orderStatus = OrderStatus.WAITING_PAYMENT;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Orders orders = (Orders) o;

        if (id != orders.id) return false;
        if (total != orders.total) return false;
        if (date != null ? !date.equals(orders.date) : orders.date != null) return false;
        if (orderStatus != null ? !orderStatus.equals(orders.orderStatus) : orders.orderStatus != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + Float.valueOf(total).hashCode();
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (orderStatus != null ? orderStatus.hashCode() : 0);
        return result;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
