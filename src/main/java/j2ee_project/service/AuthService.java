package j2ee_project.service;

import j2ee_project.dto.CustomerDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.model.user.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.Hibernate;

import java.util.Set;

public class AuthService {

    public static void logIn(String email, String password){

    }

    public static void logOut(){

    }

    public static void registerCustomer(String firsName, String lastName, String email, String phoneNumber, String password){

    }

    public static void registerModerator(String firsName, String lastName, String email, String phoneNumber, String password){

    }

    public static boolean userDataValidation(){
        return true;
    }

    public static void main(String[] args) {
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        String email = "test@email.com";
        String password = "TestPassword123";
        String phoneNumber = "0123456789";

        CustomerDTO customerDTO = new CustomerDTO(null, lastName, email, password, phoneNumber);
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDTO>> constraintViolations = validator.validate(customerDTO);

        for(ConstraintViolation<UserDTO> violation : constraintViolations){
            System.out.printf(violation.getMessage());
        }

    }

}
