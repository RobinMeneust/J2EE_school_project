package j2ee_project.dto;

import j2ee_project.model.Address;

/**
 * This class is a Data Transfer Object which contain and check Customer data
 *
 * @author Lucas VELAY
 */
public class CustomerDTO extends UserDTO{

    private Address address;

    public CustomerDTO(String firstName, String lastName, String email, String password, String confirmPassword, String phoneNumber) {
        super(firstName, lastName, email, password, confirmPassword, phoneNumber);
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "firstName=" + this.getFirstName() +
                ", lastName=" + this.getLastName() +
                ", email=" + this.getEmail() +
                ", password=" + this.getPassword() +
                ", confirmPassword=" + this.getConfirmPassword() +
                ", phoneNumber=" + this.getPhoneNumber() +
                "address=" + this.getAddress() +
                '}';
    }
}