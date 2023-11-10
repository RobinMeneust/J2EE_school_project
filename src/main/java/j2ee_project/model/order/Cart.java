package j2ee_project.model.order;

import j2ee_project.model.Discount;
import j2ee_project.model.user.Customer;
import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "idDiscount", referencedColumnName = "id")
    private Discount discount;
    @OneToOne
    @JoinColumn(name = "idCustomer", referencedColumnName = "idUser", nullable = false)
    private Customer customer;
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> cartItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cart cart = (Cart) o;

        if (id != cart.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Set<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
