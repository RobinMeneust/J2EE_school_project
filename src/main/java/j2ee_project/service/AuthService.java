package j2ee_project.service;

import j2ee_project.dao.user.UserDAO;
import j2ee_project.dto.ContactDTO;
import j2ee_project.dto.CustomerDTO;
import j2ee_project.dto.ModeratorDTO;
import j2ee_project.dto.UserDTO;
import j2ee_project.model.user.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This is a service class which contains method for authentication
 *
 * @author Lucas VELAY
 */
public class AuthService {

    /**
     * Method used to check if the login information are in the database
     *
     * @param email email
     * @param password password
     * @return the found user
     * @throws NoSuchAlgorithmException exception throws by password hash methods
     * @throws InvalidKeySpecException exception throws by password hash methods
     */
    public static User logIn(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        User user = UserDAO.getUserFromEmail(email);
        if(user == null){
            return null;
        }
        if(!HashService.validatePassword(password, user.getPassword())){
            return null;
        }
        return user;
    }

    /**
     * Register a customer in the database after hashed password generation
     * @param customerDTO customer data transfer object
     * @return the registered user
     * @throws NoSuchAlgorithmException exception throws by password hash methods
     * @throws InvalidKeySpecException exception throws by password hash methods
     */
    public static User registerCustomer(CustomerDTO customerDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        customerDTO.setPassword(HashService.generatePasswordHash(customerDTO.getPassword()));
        User customer = new Customer(customerDTO);
        UserDAO.addUser(customer);
        return customer;
    }

    /**
     * Register a moderator in the database after hashed password generation
     * @param moderatorDTO moderator data transfer object
     * @return the registered user
     * @throws NoSuchAlgorithmException exception throws by password hash methods
     * @throws InvalidKeySpecException exception throws by password hash methods
     */
    public static User registerModerator(ModeratorDTO moderatorDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        moderatorDTO.setPassword(HashService.generatePasswordHash(moderatorDTO.getPassword()));
        Moderator moderator = new Moderator(moderatorDTO);
        UserDAO.addUser(moderator);
        return moderator;
    }

    /**
     * Validate a user data transfer object
     * @param userDTO the dto to validate
     * @return a map with error message
     */
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
    }

    /**
     * Validate a contact data transfer object
     * @param contactDTO the dto to validate
     * @return a map with error message
     */
    public static Map<String, String> contactDataValidation(ContactDTO contactDTO){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<ContactDTO>> violations = validator.validate(contactDTO);
        Map<String, String> violationsMap = new HashMap<>();
        for(ConstraintViolation<ContactDTO> violation : violations){
            violationsMap.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return violationsMap;
    }

    /**
     * Check if a moderator has a precise permission
     *
     * @param user the moderator to check
     * @param typePermission the tested permission
     * @return true or false if the moderator has or no the permission
     */
    public static boolean checkModerator(User user, TypePermission typePermission){
        if(user == null){
            return false;
        }
        if(!(user instanceof Moderator)){
            return false;
        }
        Permission permission = new Permission();
        permission.setPermission(typePermission);
        if(!((Moderator) user).getPermissions().contains(permission)){
            return false;
        }
        return true;
    }

    /**
     * Get a customer object from a user object
     *
     * @param user the user
     * @return the customer or null
     */
    public static Customer getCustomer(User user) {
        if(user instanceof Customer) {
            return (Customer) user;
        } else {
            return null;
        }
    }

    /**
     * Get a moderator object from a user object
     *
     * @param user the user
     * @return the moderator or null
     */
    public static Moderator getModerator(User user) {
        if(user instanceof Moderator) {
            return (Moderator) user;
        } else {
            return null;
        }
    }
}