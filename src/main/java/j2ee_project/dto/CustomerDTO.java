package j2ee_project.dto;

public class CustomerDTO extends UserDTO{

    public CustomerDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        super(firstName, lastName, email, password, confirmPassword, phoneNumber);
    }

}