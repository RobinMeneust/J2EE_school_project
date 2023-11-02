package j2ee_project.service;

import j2ee_project.dto.CustomerDTO;
import j2ee_project.dto.ModeratorDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.model.user.Customer;
import j2ee_project.model.user.Moderator;
import j2ee_project.model.user.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Hibernate;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Set;

public class AuthService {

    public static void logIn(String email, String password){

    }

    public static void logOut(){

    }

    public static void registerCustomer(CustomerDTO customerDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        customerDTO.setPassword(HashService.generatePasswordHash(customerDTO.getPassword()));
        Customer customer = new Customer(customerDTO);
    }

    public static void registerModerator(ModeratorDTO moderatorDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        moderatorDTO.setPassword(HashService.generatePasswordHash(moderatorDTO.getPassword()));
        Moderator moderator = new Moderator(moderatorDTO);
    }

    public static void userDataValidation(UserDTO userDTO){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(userDTO);

        for(ConstraintViolation<UserDTO> violation : constraintViolations){
            System.out.printf(violation.getMessage());
        }
    }

    public static void main(String[] args) {
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        String email = "test@email.com";
        String password = "TestPassword123";
        String phoneNumber = "0123456789";

        CustomerDTO customerDTO = new CustomerDTO(null, lastName, email, password, phoneNumber);
        userDataValidation(customerDTO);

    }

    // TODO DAO to exchange with the db
    // TODO finalize data verification and AuthService
}