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
    @Column(name = "stockQuantity", nullable = true)
    private Integer stockQuantity;
    @Basic
    @Column(name = "unitPrice", nullable = true, precision = 0)
    private float unitPrice;
    @Basic
    @Column(name = "description", nullable = true, length = 300)
    private String description;
    @Basic
    @Column(name = "imageUrl", nullable = true, length = 500)
    private String imageUrl;
    @Basic
    @Column(name = "weight", nullable = true, precision = 0)
    private float weight;
    @Basic
    @Column(name = "idCategory", nullable = true)
    private Integer idCategory;

    public Product() {
    }

    public Product(String name, int stockQuantity, float unitPrice, String description, String imageUrl, float weight, Category category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imageUrl = imageUrl;
        this.weight = weight;
        this.idCategory = category.getId();
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

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
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

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id && Objects.equals(name, product.name) && Objects.equals(stockQuantity, product.stockQuantity) && Objects.equals(unitPrice, product.unitPrice) && Objects.equals(description, product.description) && Objects.equals(imageUrl, product.imageUrl) && Objects.equals(weight, product.weight) && Objects.equals(idCategory, product.idCategory);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, stockQuantity, unitPrice, description, imageUrl, weight, idCategory);
    }
}
