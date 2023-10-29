package j2ee_project.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
public class Mail {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "fromAddress", nullable = true, length = 50)
    private String fromAddress;
    @Basic
    @Column(name = "toAddress", nullable = true, length = 50)
    private String toAddress;
    @Basic
    @Column(name = "subject", nullable = true, length = 50)
    private String subject;
    @Basic
    @Column(name = "body", nullable = true, length = 300)
    private String body;
    @Basic
    @Column(name = "date", nullable = true)
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return id == mail.id && Objects.equals(fromAddress, mail.fromAddress) && Objects.equals(toAddress, mail.toAddress) && Objects.equals(subject, mail.subject) && Objects.equals(body, mail.body) && Objects.equals(date, mail.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromAddress, toAddress, subject, body, date);
    }
}
