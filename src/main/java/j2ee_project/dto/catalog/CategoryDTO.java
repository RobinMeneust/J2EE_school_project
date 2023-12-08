package j2ee_project.dto.catalog;

import j2ee_project.model.Discount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    @NotBlank(message = "Name can not be blank.")
    @Size(max = 30, message = "Name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-']*$", message = "Name is not valid : only letters and -' are authorized.")
    private String name;
    @NotBlank(message = "Description can not be blank.")
    @Size(max = 300, message = "Description can not exceed 300 characters.")
    private String description;
    private Discount discount;

    public CategoryDTO(String name, String description, Discount discount) {
        this.name = name;
        this.description = description;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Discount getDiscount() {
        return discount;
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
                "name=" + name +
                ", description=" + description +
                ", discount=" + discount +
                '}';
    }
}
