package j2ee_project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserDTO {

    @NotBlank()
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z\\-]$", message = "is not valid.")
    private String firstName;
    @NotBlank()
    @Size(max = 30)
    @Pattern(regexp = "^[a-zA-Z\\-]{1,30}$", message = "is not valid.")
    private String lastName;
    @Email()
    private String email;
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "is not valid.")
    private String password;
    @NotBlank()
    @Size(max = 30)
    @Pattern(regexp = "^[0-9]{10}$", message = "is not valid.")
    private String phoneNumber;

    public UserDTO(String firstName, String lastName, String email, String password, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
