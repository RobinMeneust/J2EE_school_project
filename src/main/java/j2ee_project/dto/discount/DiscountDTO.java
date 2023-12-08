package j2ee_project.dto.discount;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.sql.Date;

public class DiscountDTO {
    @NotBlank(message = "Name can not be blank.")
    @Size(max = 30, message = "Name can not exceed 30 characters.")
    @Pattern(regexp = "^[a-zA-ZÀ-ÖØ-öø-ÿ\\-']*$", message = "Name is not valid : only letters and -' are authorized.")
    private String name;
    @NotBlank(message = "Start date can not be blank.")
    @Pattern(regexp = "^\\d{2}\\/\\d{2}\\/\\d{4}$", message = "Start date is not valid.")
    private Date startDate;
    @NotBlank(message = "End date can not be blank.")
    @Pattern(regexp = "^\\d{2}\\/\\d{2}\\/\\d{4}$", message = "End date is not valid.")
    private Date endDate;
    @NotBlank(message = "Discount percentage can not be blank.")
    @Pattern(regexp = "^\\d+(\\.[05])?$", message = "Discount percentage is not valid.")
    private int discountPercentage;

    public DiscountDTO(String name, Date startDate, Date endDate, int discountPercentage) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountPercentage = discountPercentage;
    }

    public String getName() {
        return name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    @Override
    public String toString() {
        return "DiscountDTO{" +
                "name=" + name +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", discountPercentage=" + discountPercentage +
                '}';
    }
}
