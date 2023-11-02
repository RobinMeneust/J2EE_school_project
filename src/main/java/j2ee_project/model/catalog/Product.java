package j2ee_project.model.catalog;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Product {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = true, length = 30)
    private String name;
    @Basic
    @Column(name = "stockQuantity", nullable = false)
    private int stockQuantity;
    @Basic
    @Column(name = "unitPrice", nullable = false, precision = 0)
    private double unitPrice;
    @Basic
    @Column(name = "description", nullable = true, length = 300)
    private String description;
    @Basic
    @Column(name = "imageUrl", nullable = true, length = 500)
    private String imageUrl;
    @Basic
    @Column(name = "weight", nullable = true, precision = 0)
    private float weight;
    @ManyToOne
    @JoinColumn(name = "idCategory", referencedColumnName = "id", nullable = false)
    private Category category;

    public Product() {
    }

    public Product(String name, int stockQuantity, float unitPrice, String description, String imageUrl, float weight, Category category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.category = category;
    }

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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (id != product.id) return false;
        if (stockQuantity != product.stockQuantity) return false;
        if (Double.compare(unitPrice, product.unitPrice) != 0) return false;
        if (!Objects.equals(name, product.name)) return false;
        if (!Objects.equals(description, product.description)) return false;
        if (!Objects.equals(imageUrl, product.imageUrl)) return false;
        if (!Objects.equals(weight, product.weight)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stockQuantity, unitPrice, description, imageUrl, weight, category);
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
