package j2ee_project.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class CartItem {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "quantity", nullable = true)
    private Integer quantity;
    @Basic
    @Column(name = "idCart", nullable = true)
    private Integer idCart;
    @Basic
    @Column(name = "idProduct", nullable = true)
    private Integer idProduct;
    @Basic
    @Column(name = "idOrder", nullable = true)
    private Integer idOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getIdCart() {
        return idCart;
    }

    public void setIdCart(Integer idCart) {
        this.idCart = idCart;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return id == cartItem.id && Objects.equals(quantity, cartItem.quantity) && Objects.equals(idCart, cartItem.idCart) && Objects.equals(idProduct, cartItem.idProduct) && Objects.equals(idOrder, cartItem.idOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, quantity, idCart, idProduct, idOrder);
    }
}
