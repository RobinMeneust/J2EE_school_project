package j2ee_project.model.user;

import jakarta.persistence.*;

import java.sql.Date;
import java.time.Duration;
import java.time.Instant;
import java.util.Objects;

@Entity
public class ForgottenPassword {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "idUser")
    private User user;
    @Basic
    @Column(name = "token", nullable = false, length = 50)
    private String token;
    @Basic
    @Column(name = "expiryDate", nullable = false)
    private Date expiryDate;

    public ForgottenPassword(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiryDate = new Date(Date.from(Instant.now().plus(Duration.ofDays(1))).getTime());
    }

    public ForgottenPassword() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ForgottenPassword that = (ForgottenPassword) o;
        return id == that.id && Objects.equals(user, that.user) && Objects.equals(token, that.token) && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, token, expiryDate);
    }

    @Override
    public String toString() {
        return "ForgottenPassword{" +
                "id=" + id +
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
