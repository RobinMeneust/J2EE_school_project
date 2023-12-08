package j2ee_project.dto.catalog;

import j2ee_project.model.catalog.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {
    @NotBlank(message = "Name can not be blank.")
    @Size(max = 30, message = "Name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-']*$", message = "Name is not valid : only letters and -' are authorized.")
    private String name;
    @NotBlank(message = "Stock quantity can not be blank.")
    @Pattern(regexp = "^[0-9]*$", message = "Stock quantity is not valid.")
    private Integer stockQuantity;
    @NotBlank(message = "Unit price can not be blank.")
    @Pattern(regexp = "^\\d+(\\.\\d{1,2})?$", message = "Unit price is not valid.")
    private float unitPrice;
    @Size(max = 300, message = "Description can not exceed 300 characters.")
    private String description;
    @Size(max = 500, message = "Image path can not exceed 500 characters.")
    private String imagePath;
    @Pattern(regexp = "^\\d+(\\.\\d*)?$", message = "Weight is not valid.")
    private Float weight;
    @NotBlank(message = "Category can not be blank.")
    private Category category;


    public ProductDTO(String name, Integer stockQuantity, float unitPrice, String description, String imagePath, Float weight, Category category) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unitPrice = unitPrice;
        this.description = description;
        this.imagePath = imagePath;
        this.weight = weight;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public Float getWeight() {
        return weight;
    }

    public Category getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "name=" + name +
                ", stockQuantity=" + stockQuantity +
                ", unitPrice=" + unitPrice +
                ", description=" + description +
                ", imagePath=" + imagePath +
                ", weight=" + weight +
                ", category=" + category +
                '}';
    }
}
