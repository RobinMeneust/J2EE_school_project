package j2ee_project.model.user;

import j2ee_project.model.Address;
import j2ee_project.model.order.Cart;
import j2ee_project.model.loyalty.LoyaltyAccount;
import j2ee_project.model.order.Orders;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@PrimaryKeyJoinColumn(name = "idUser")
public class Customer extends User{

    @OneToOne(mappedBy = "customer")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "idAddress", referencedColumnName = "id")
    private Address address;
    @ManyToOne
    @JoinColumn(name = "idLoyaltyAccount", referencedColumnName = "id")
    private LoyaltyAccount loyaltyAccount;
    @OneToMany(mappedBy = "customer")
    private Set<Orders> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (this.getId() != customer.getId()) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return this.getId();
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LoyaltyAccount getLoyaltyAccount() {
        return loyaltyAccount;
    }

    public void setLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
        this.loyaltyAccount = loyaltyAccount;
    }

    public Set<Orders> getOrders() {
        return orders;
    }

    public void setOrders(Set<Orders> orders) {
        this.orders = orders;
    }
}
