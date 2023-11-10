package j2ee_project.service;

import j2ee_project.dao.user.UserDAO;
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

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class AuthService {

    public static void logIn(String email, String password){

    }

    public static void logOut(){

    }

    public static User registerCustomer(CustomerDTO customerDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        customerDTO.setPassword(HashService.generatePasswordHash(customerDTO.getPassword()));
        User customer = new Customer(customerDTO);
        UserDAO.addUser(customer);
        return customer;
    }

    public static User registerModerator(ModeratorDTO moderatorDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        moderatorDTO.setPassword(HashService.generatePasswordHash(moderatorDTO.getPassword()));
        Moderator moderator = new Moderator(moderatorDTO);
        UserDAO.addUser(moderator);
        return moderator;
    }

    public static Map<String, String> userDataValidation(UserDTO userDTO){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserDTO>> violations = validator.validate(userDTO);
        Map<String, String> violationsMap = new HashMap<>();
        for(ConstraintViolation<UserDTO> violation : violations){
            violationsMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            violationsMap.put("confirmPassword", "Password and Confirm Password must match.");
        }
        return violationsMap;

      /*  for(ConstraintViolation<UserDTO> violation : constraintViolations){
            System.out.printf(violation.getMessage());
        }*/
    }

    public static void main(String[] args) {
        String firstName = "TestFirstName";
        String lastName = "TestLastName";
        String email = "test@email.com";
        String password = "#TestPassword123";
        String passwordConfirmation = "#TestPassword123";

        String phoneNumber = "0123456789";

        CustomerDTO customerDTO = new CustomerDTO(firstName, lastName, email, password, passwordConfirmation, phoneNumber);
        Map<String, String> constraintViolations = userDataValidation(customerDTO);
        if (!constraintViolations.isEmpty()){
            for(String violation : constraintViolations.values()){
                System.out.print(violation);
            }
            return;
        }
        try {
            User customer = registerCustomer(customerDTO);
            System.out.print("Reussite");

        }catch (NoSuchAlgorithmException | InvalidKeySpecException exception){
            System.out.print("Error");
        }
        catch (Exception exception){
            System.out.printf("Error !!!!! : ");
            System.out.print(exception);
        }

    }

    // TODO DAO to exchange with the db
    // TODO finalize data verification and AuthService
}