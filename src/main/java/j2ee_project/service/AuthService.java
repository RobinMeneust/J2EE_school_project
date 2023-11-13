package j2ee_project.service;

import j2ee_project.dao.user.UserDAO;
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

public class AuthService {

    public static User logIn(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        User user = UserDAO.getUserFromEmail(email);
        System.out.println(user);
        if(user == null){
            return null;
        }
        if(!HashService.validatePassword(password, user.getPassword())){
            return null;
        }
        return user;
    }

    public static void logOut(){

    }

    public static User registerCustomer(CustomerDTO customerDTO) throws NoSuchAlgorithmException, InvalidKeySpecException {
        customerDTO.setPassword(HashService.generatePasswordHash(customerDTO.getPassword()));
        System.out.println(customerDTO);
        User customer = new Customer(customerDTO);
        System.out.println(customer);
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

    public static Customer getCustomer(User user) {
        if(user instanceof Customer) {
            return (Customer) user;
        } else {
            return null;
        }
    }
}