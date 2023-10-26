package j2ee_project.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Customer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "idUser", nullable = true)
    private Integer idUser;
    @Basic
    @Column(name = "idAddress", nullable = false)
    private int idAddress;
    @Basic
    @Column(name = "idLoyaltyAccount", nullable = true)
    private Integer idLoyaltyAccount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public Integer getIdLoyaltyAccount() {
        return idLoyaltyAccount;
    }

    public void setIdLoyaltyAccount(Integer idLoyaltyAccount) {
        this.idLoyaltyAccount = idLoyaltyAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id && idAddress == customer.idAddress && Objects.equals(idUser, customer.idUser) && Objects.equals(idLoyaltyAccount, customer.idLoyaltyAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idUser, idAddress, idLoyaltyAccount);
    }
}
